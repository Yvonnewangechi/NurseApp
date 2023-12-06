package com.example.nurseapplication.models

data class InvoiceModel(
    val appointment_date: String,
    val appointment_time: String,
    val book_id: Int,
    val booked_for: String,
    val dependant_id: Int,
    val invoice_no: String,
    val lab_id: Int,
    val latitude: String,
    val longitude: String,
    val member_id: Int,
    val status: String,
    val test_id: Int,
    val where_taken: String
)