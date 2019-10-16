package com.patres.automation

class Dog(animalType: DogType) : Animal<DogType>(animalType) {
    fun doSomething() {
        animalType.runDogTypeFunction() // animalType is always DogType
    }
}

abstract class Animal<T: AnimalType>(open val animalType: T)
interface AnimalType

enum class DogType() : AnimalType {
    DOG1, DOG2;
    fun runDogTypeFunction() {}
}

enum class CatType() : AnimalType {
    CAT1, CAT2;
    fun runCatTypeFunction() {}
}
