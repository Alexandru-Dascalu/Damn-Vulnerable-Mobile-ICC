package uk.ac.swansea.dascalu.dvmicc.call_logger.fragments

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
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import uk.ac.swansea.dascalu.dvmicc.call_logger.R
import uk.ac.swansea.dascalu.dvmicc.call_logger.adapters.recycler_view.LogFragmentAdapter
import uk.ac.swansea.dascalu.dvmicc.call_logger.model.Call

import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("UseRequireInsteadOfGet")
class LogFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.log_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.call_log_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val readCallLogPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                recyclerView.adapter = LogFragmentAdapter(getCallLogData())
            } else {
                Toast.makeText(context, "Call Logger needs permission to read contacts",
                    Toast.LENGTH_LONG).show()
            }
        }

        if(ContextCompat.checkSelfPermission(context!!, Manifest.permission.READ_CALL_LOG) ==
            PackageManager.PERMISSION_GRANTED) {

            recyclerView.adapter = LogFragmentAdapter(getCallLogData())
        } else {
            readCallLogPermissionLauncher.launch(Manifest.permission.READ_CALL_LOG)
        }
    }

    private fun getCallLogData() : List<Call> {
        val callList : MutableList<Call> = mutableListOf<Call>()
        val callLogs: Uri = Uri.parse("content://call_log/calls")
        val cursorCallLogs: Cursor? = context!!.contentResolver.query(callLogs, null, null, null, null)

        if(cursorCallLogs != null) {
            cursorCallLogs.moveToFirst()

            while(cursorCallLogs.moveToNext()) {
                val stringNumber: String = cursorCallLogs.getString(cursorCallLogs.getColumnIndex(CallLog.Calls.NUMBER))
                val stringName: String? = cursorCallLogs.getString(cursorCallLogs.getColumnIndex(CallLog.Calls.CACHED_NAME))
                val stringDuration: String = cursorCallLogs.getString(cursorCallLogs.getColumnIndex(CallLog.Calls.DURATION))
                val stringType: String = cursorCallLogs.getString(cursorCallLogs.getColumnIndex(CallLog.Calls.TYPE))

                val date : Long = cursorCallLogs.getLong(cursorCallLogs.getColumnIndex(CallLog.Calls.DATE))
                val dateString : String = getDateString(date)

                val call = Call(stringName, stringNumber, dateString, stringDuration)
                callList.add(call)
            }

            cursorCallLogs.close()
        }

        return callList
    }

    private fun getDateString(milliseconds: Long) : String {
        val date = Date(milliseconds)
        val dateFormat = SimpleDateFormat("dd:MM:yy:HH:mm:ss")

        return dateFormat.format(date)
    }
}