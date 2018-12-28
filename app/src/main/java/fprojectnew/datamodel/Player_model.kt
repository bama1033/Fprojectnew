package com.android.academy.fprojectnew.datamodel

data class Player_model(val id: String?,
                        val name: String?,
                        val team: String?,
                        val nationality:String?,
                        val number: Int?,
                        val position: String?,
                        val stats: List<String>,
                        val stats2: Map<String, Int>?)