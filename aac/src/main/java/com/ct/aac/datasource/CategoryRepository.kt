package com.ct.aac.datasource

import com.ct.aac.paging.ServiceApi
import com.ct.aac.vo.Category
import com.ct.aac.vo.Listing

class CategoryRepository(private val serviceApi: ServiceApi) {

    fun getCategory(): Listing<Category> {
        val factory = CategoryDataSourceFactory(serviceApi)
        val pagedList = factory.toMyLiveData(
            pageSize = 10
        )

        return Listing(
            pagedList = pagedList,
            refresh = {
                factory.dataSource.value?.invalidate()
            }
        )
    }

    fun refreshCategory() {

    }
}