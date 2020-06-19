package com.example.lendvest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.lendvest.R
import com.example.lendvest.utils.setViewTitle
import kotlinx.android.synthetic.main.fragment_file_data.*

class FileDataFragment : SuperFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_file_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewTitle(activity=(activity as AppCompatActivity), title="Document View")

        val imageResourceId = arguments?.getInt("IMAGE_RESOURCE_ID")

        photo_view.setImageDrawable(getDrawable(imageResourceId!!))

    }

}
