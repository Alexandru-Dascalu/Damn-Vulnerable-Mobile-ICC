package uk.ac.swansea.dascalu.dvmicc.call_logger2.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.CallLog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import uk.ac.swansea.dascalu.dvmicc.call_logger2.R
import uk.ac.swansea.dascalu.dvmicc.call_logger2.adapters.recycler_view.LogFragmentAdapter
import uk.ac.swansea.dascalu.dvmicc.call_logger2.model.Call

import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("UseRequireInsteadOfGet")
class CallLogFragment(val position: Int) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.call_log_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.call_log_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        if(ContextCompat.checkSelfPermission(context!!, Manifest.permission.READ_CALL_LOG) ==
            PackageManager.PERMISSION_GRANTED) {

            recyclerView.adapter = LogFragmentAdapter(getCallLogData())
        }
    }

    private fun getCallLogData() : List<Call> {
        val callList : MutableList<Call> = mutableListOf<Call>()
        var cursorCallLogs: Cursor? =  null

        if(position == 0) {
            cursorCallLogs = getAllCallsCursor()
        } else if(position == 1) {
            cursorCallLogs = getMissedCallsCursor()
        } else {
            cursorCallLogs = getUnknownCallsCursor()
        }

        if(cursorCallLogs != null) {

            while(cursorCallLogs.moveToNext()) {
                val stringNumber: String = cursorCallLogs.getString(cursorCallLogs.getColumnIndex(CallLog.Calls.NUMBER))
                val stringName: String? = cursorCallLogs.getString(cursorCallLogs.getColumnIndex(CallLog.Calls.CACHED_NAME))
                val stringDuration: String = cursorCallLogs.getString(cursorCallLogs.getColumnIndex(CallLog.Calls.DURATION))

                val date : Long = cursorCallLogs.getLong(cursorCallLogs.getColumnIndex(CallLog.Calls.DATE))
                val dateString : String = getDateString(date)

                val call = Call(stringName, stringNumber, dateString, stringDuration)
                callList.add(call)
            }

            cursorCallLogs.close()
        }

        return callList
    }

    private fun getAllCallsCursor() : Cursor? {
        val callLogs: Uri = Uri.parse("content://call_log/calls")
        val cursorCallLogs = context!!.contentResolver.query(callLogs, null, null,
                null, "${CallLog.Calls.DATE} DESC")

        return cursorCallLogs
    }

    private fun getMissedCallsCursor() : Cursor? {
        val callLogs: Uri = Uri.parse("content://call_log/calls")
        val cursorCallLogs = context!!.contentResolver.query(callLogs, null,
                "${CallLog.Calls.TYPE}=${CallLog.Calls.MISSED_TYPE}",
                null, "${CallLog.Calls.DATE} DESC")

        return cursorCallLogs
    }

    private fun getUnknownCallsCursor() : Cursor? {
        val callLogs: Uri = Uri.parse("content://call_log/calls")
        val cursorCallLogs = context!!.contentResolver.query(callLogs, null,
                "(${CallLog.Calls.CACHED_NAME} IS NULL) or ${CallLog.Calls.CACHED_NAME}=\"\"",
                null, "${CallLog.Calls.DATE} DESC")

        return cursorCallLogs
    }

    private fun getDateString(milliseconds: Long) : String {
        val date = Date(milliseconds)
        val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")

        return dateFormat.format(date)
    }
}