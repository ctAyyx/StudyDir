package com.ct.animator.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ct.animator.databinding.FragmentValueBinding
import com.ct.animator.handler.AnimatorHandler

/**
 * ValueAnimator的使用
 * */
class ValueFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentValueBinding.inflate(inflater, container, false).apply {
        handler = AnimatorHandler()
    }.root
}