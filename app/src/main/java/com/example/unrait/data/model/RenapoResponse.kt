package com.example.unrait.data.model

data class RenapoResponse(
    val error: Boolean,
    val code_error: Int,
    val error_message: String?,
    val response: ResponseData?
)

data class ResponseData(
    val Solicitante: Solicitante
)

data class Solicitante(
    val CURP: String,
    val Nombres: String,
    val ApellidoPaterno: String,
    val ApellidoMaterno: String?,
    val ClaveSexo: String,
    val Sexo: String,
    val FechaNacimiento: String,
    val Nacionalidad: String,
    val ClaveEntidadNacimiento: String,
    val EntidadNacimiento: String,
    val ClaveStatusCurp: String,
    val StatusCurp: String
)