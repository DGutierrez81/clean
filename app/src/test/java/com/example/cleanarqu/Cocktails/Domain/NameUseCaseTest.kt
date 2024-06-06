package com.example.cleanarqu.Cocktails.Domain

import com.example.cleanarqu.Cocktails.Model.Repository
import com.example.cleanarqu.Cocktails.ui.State.CocktailState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class NameUseCaseTest{

    @RelaxedMockK
    private lateinit var qouteRepository: Repository

    lateinit var nameUseCase: NameUseCase

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        nameUseCase = NameUseCase(qouteRepository)
    }

    @Test
    fun vamosProbando() = runBlocking {
        //Given
        coEvery { qouteRepository.vCocktail("") } returns CocktailState()
       // if (coEvery { qouteRepository.vCocktail("") } == CocktailState()){


        //When
        nameUseCase("")
        assertEquals(qouteRepository.vCocktail(""),CocktailState() )
        //Then
        coVerify(exactly = 1) { qouteRepository.vCocktail("") }
        //if(CocktailState())assertTrue()
    }

}