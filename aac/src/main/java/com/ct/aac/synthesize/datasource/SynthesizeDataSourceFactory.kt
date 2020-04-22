package com.ct.aac.synthesize.datasource

import androidx.paging.DataSource
import com.ct.aac.synthesize.api.GankServiceApi
import com.ct.aac.synthesize.vo.Category

/**
 * @ClassName : SynthesizeDatasourceFactory
 * @CreateDate : 2020/4/15 9:51
 * @Author : CT
 * @Description :
 *
 */
class SynthesizeDataSourceFactory(
    val category: String,
    val serviceApi: GankServiceApi,
    val page: Int = 1
) :
    DataSource.Factory<Int, Category>() {
    override fun create(): DataSource<Int, Category> {
        val result = SynthesizeDataSource(category, serviceApi,page)
        return result
    }
}