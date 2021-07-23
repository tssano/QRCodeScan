package com.mitsui.tkmid.machineinfoviewer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.github.barteksc.pdfviewer.PDFView
import com.mitsui.tkmid.machineinfoviewer.databinding.FragmentPdfViewBinding
import kotlinx.android.synthetic.main.fragment_pdf_view.*


class PDFViewFragment : Fragment() {

    private var cdNumber: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val arguments = PDFViewFragmentArgs.fromBundle(arguments!!)

        val binding = DataBindingUtil.inflate<FragmentPdfViewBinding>(
            inflater,
            R.layout.fragment_pdf_view, container, false
        )

        cdNumber = arguments.qrReadValue.substring(0,5)

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        Log.i("CallCheck", "PDFView onStart called")
        //pdfView.fromAsset("10410.pdf").load()
        pdfView.fromAsset(cdNumber + ".pdf").load()

    }

}