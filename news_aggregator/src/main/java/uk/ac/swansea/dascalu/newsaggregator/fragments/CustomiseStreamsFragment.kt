package uk.ac.swansea.dascalu.newsaggregator.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

import uk.ac.swansea.dascalu.newsaggregator.model.Database
import uk.ac.swansea.dascalu.newsaggregator.R
import uk.ac.swansea.dascalu.newsaggregator.adapters.NewsStreamNameAdapter

class CustomiseStreamsFragment () : Fragment() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val customiseRootView = inflater.inflate(R.layout.customise_streams_fragment, container, false)

        val addStreamButton = customiseRootView.findViewById<ImageButton>(R.id.add_stream_button)
        addStreamButton.setOnClickListener { _ -> onAddCustomStream() }

        val removeStreamButton = customiseRootView.findViewById<ImageButton>(R.id.remove_stream_button)
        removeStreamButton.setOnClickListener {view -> onRemoveCustomStream(view)}

        return customiseRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       recyclerView = view.findViewById<RecyclerView>(R.id.news_stream_names_recycler_view)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = NewsStreamNameAdapter(activity as AppCompatActivity)
    }

    fun onAddCustomStream() {
        val streamNameInput = EditText(context)

        val dialog = MaterialAlertDialogBuilder(context!!).setTitle(resources.getString(R.string.add_stream_dialog_title))
            .setView(streamNameInput)
            .setPositiveButton(resources.getString(R.string.add_msg), null)
            .setNeutralButton(resources.getString(R.string.cancel_msg)) { _, _ -> }
            .show()

        val addButton: Button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        addButton.setOnClickListener() { _ ->
            if(streamNameInput.text.toString() != "") {
                Database.instance.addCustomNewsStream(streamNameInput.text.toString())
                recyclerView.adapter!!.notifyDataSetChanged()
                dialog.dismiss()
            } else {
                Snackbar.make(this.view!!, resources.getString(R.string.stream_name_type_in_message),
                    Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    fun onRemoveCustomStream(fragmentView: View) {
        val streamNameInput = EditText(context)

        val dialog = MaterialAlertDialogBuilder(context!!).setTitle(resources.getString(
            R.string.remove_stream_dialog_title))
            .setView(streamNameInput)
            .setPositiveButton(resources.getString(R.string.remove_msg), null)
            .setNeutralButton(resources.getString(R.string.cancel_msg)) { _, _ -> }
            .show()

        val addButton: Button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        addButton.setOnClickListener() { _ ->
            if (streamNameInput.text.toString() != "") {
                if (streamNameInput.text.toString() == context!!.resources.getString(R.string.all)) {
                    Snackbar.make(this.view!!, resources.getString(R.string.remove_all_stream_msg),
                        Snackbar.LENGTH_LONG).show()
                } else {
                    Database.instance.removeCustomNewsStream(streamNameInput.text.toString())
                    recyclerView.adapter!!.notifyDataSetChanged()
                    dialog.dismiss()
                }
            } else {
                Snackbar.make(this.view!!, resources.getString(R.string.stream_name_type_in_message),
                    Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}