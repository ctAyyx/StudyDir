package com.ct.animator

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ct.animator.databinding.FragmentHomeBinding
import com.ct.animator.ui.TransitionActivity01
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentHomeBinding.inflate(inflater, container, false).apply {

        btnAnimHomeValue.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_valueFragment)
        }

        btnAnimHomeActivity.setOnClickListener {
            requireActivity().startActivity(Intent(activity, TransitionActivity01::class.java))
        }
    }.root
}