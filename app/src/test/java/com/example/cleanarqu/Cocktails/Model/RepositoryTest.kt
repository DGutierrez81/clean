package com.example.cleanarqu.Cocktails.Model

import com.example.cleanarqu.Cocktails.ui.State.CocktailState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test

class RepositoryTest {

    @RelaxedMockK
    private lateinit var qouteApi: CocktailsView

    lateinit var  getApiUseCase: Repository

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        getApiUseCase = Repository(qouteApi)
    }


/*
    @Test
    fun vCocktail() = runBlocking {
        //Given
        coEvery { qouteApi.getCocktail("") } returns

        //When
       // qouteApi("")

        //Then
        coVerify(exactly = 1) { getApiUseCase.vCocktail("") }
    }

 */
}