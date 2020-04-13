package com.ct.dagger.component

import com.ct.dagger.vo.User
import dagger.Component

@Component
interface UserComponent {

    fun inject(): User
}