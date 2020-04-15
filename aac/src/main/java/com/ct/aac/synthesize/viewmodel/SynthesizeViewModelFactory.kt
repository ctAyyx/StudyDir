package com.ct.aac.synthesize.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ct.aac.synthesize.repository.SynthesizeRepository

/**
 * @ClassName : SynthesizeViewModelFactory
 * @CreateDate : 2020/4/15 10:20
 * @Author : CT
 * @Description :
 *
 */
class SynthesizeViewModelFactory(val repository: SynthesizeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SynthesizeViewModel(repository) as T
    }
}