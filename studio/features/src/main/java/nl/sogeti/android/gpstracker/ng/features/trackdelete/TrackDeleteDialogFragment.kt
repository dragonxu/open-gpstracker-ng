package nl.sogeti.android.gpstracker.ng.features.trackdelete

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableBoolean
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import nl.sogeti.android.gpstracker.v2.sharedwear.util.observe
import nl.sogeti.android.opengpstrack.ng.features.R
import nl.sogeti.android.opengpstrack.ng.features.databinding.FragmentDeleteDialogBinding

class TrackDeleteDialogFragment : DialogFragment() {

    lateinit var presenter: TrackDeletePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val uri = arguments?.get(ARG_URI) as Uri
        presenter = ViewModelProviders.of(this).get(TrackDeletePresenter::class.java)
        presenter.trackUri = uri
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentDeleteDialogBinding>(inflater, R.layout.fragment_delete_dialog, container, false)
        binding.model = presenter.viewModel
        binding.presenter = presenter
        presenter.viewModel.dismiss.observe { sender ->
            if (sender is ObservableBoolean && sender.get())
                dismiss()
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        presenter.start()
    }

    override fun onStop() {
        presenter.stop()
        super.onStop()
    }


    companion object {

        private const val ARG_URI = "ARGUMENT_URI"

        fun newInstance(uri: Uri): TrackDeleteDialogFragment {
            val arguments = Bundle()
            arguments.putParcelable(ARG_URI, uri)
            val fragment = TrackDeleteDialogFragment()
            fragment.arguments = arguments

            return fragment
        }
    }
}
