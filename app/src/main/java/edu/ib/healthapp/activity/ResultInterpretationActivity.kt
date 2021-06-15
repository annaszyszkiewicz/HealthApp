package edu.ib.healthapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import edu.ib.healthapp.DataBaseHelper
import edu.ib.healthapp.R
import edu.ib.healthapp.TableInfo
import edu.ib.healthapp.databinding.ActivityResultInterpretationBinding
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class ResultInterpretationActivity : AppCompatActivity() {

    private var userId = 0
    private var type = ""
    private var value = ""
    private var unit = ""
    private lateinit var binding: ActivityResultInterpretationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultInterpretationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val intent = intent;

        if (intent.hasExtra("user_id")) userId = intent.getIntExtra("user_id", 0)
        if (intent.hasExtra("type")) type = intent.getStringExtra("type").toString()
        if (intent.hasExtra("value")) value = intent.getStringExtra("value").toString()
        if (intent.hasExtra("unit")) unit = intent.getStringExtra("unit").toString()

        val resultText = "$value $unit"
        binding.txtResultInterpretation.text = resultText

        var valueNoPressure = 0.0
        var valuePressure1 = 0.0
        var valuePressure2 = 0.0

        if (type == "ciśnienie") {
            val valueTable = value.split("/")
            valuePressure1 = valueTable[0].toDouble()
            valuePressure2 = valueTable[1].toDouble()
        } else {
            valueNoPressure = value.toDouble()

        }

        val dbHelper = DataBaseHelper(applicationContext)
        val db = dbHelper.writableDatabase

        val cursor = db.query(
            TableInfo.TABLE_USER,
            arrayOf(
                TableInfo.TABLE_USER_COLUMN_BIRTH,
                TableInfo.TABLE_USER_COLUMN_HEIGHT,
            ),
            TableInfo.TABLE_USER_ID + "=?", arrayOf(userId.toString()),
            null, null, null
        )

        cursor.moveToNext()
        val dateOfBirth = LocalDate.parse(cursor.getString(0))
        val height = cursor.getDouble(1)
        cursor.close()

        val text = binding.txtInterpetation

        val yearsBetween = ChronoUnit.YEARS.between(dateOfBirth, LocalDate.now())
        when (type) {
            "tętno" -> {
                if (yearsBetween < 1) {
                    if (valueNoPressure > 135) {
                        text.text = getString(R.string.highHeartRate)
                    } else if (valueNoPressure in 125.0..135.0) {
                        text.text = getString(R.string.normal_result)
                    } else {
                        text.text = getString(R.string.lowHeartRate)
                    }
                } else if (yearsBetween in 1..17) {
                    if (valueNoPressure > 105) {
                        text.text = getString(R.string.highHeartRate)
                    } else if (valueNoPressure in 95.0..105.0) {
                        text.text = getString(R.string.normal_result)
                    } else {
                        text.text = getString(R.string.lowHeartRate)
                    }
                } else if (yearsBetween in 10..17) {
                    if (valueNoPressure > 90) {
                        text.text = getString(R.string.highHeartRate)
                    } else if (valueNoPressure in 80.0..90.0) {
                        text.text = getString(R.string.normal_result)
                    } else {
                        text.text = getString(R.string.lowHeartRate)
                    }
                } else if (yearsBetween in 18..64) {
                    if (valueNoPressure > 75) {
                        text.text = getString(R.string.highHeartRate)
                    } else if (valueNoPressure in 65.0..75.0) {
                        text.text = getString(R.string.normal_result)
                    } else {
                        text.text = getString(R.string.lowHeartRate)
                    }
                } else {
                    if (valueNoPressure > 65) {
                        text.text = getString(R.string.highHeartRate)
                    } else if (valueNoPressure in 55.0..65.0) {
                        text.text = getString(R.string.normal_result)
                    } else {
                        text.text = getString(R.string.lowHeartRate)
                    }
                }
            }
            "poziom glukozy" -> {
                if (yearsBetween <= 70) {
                    if (valueNoPressure > 99) {
                        text.text = getString(R.string.highSugar)
                    } else if (valueNoPressure in 70.0..99.0) {
                        text.text = getString(R.string.normal_result)
                    } else if (valueNoPressure < 55) {
                        text.text = getString(R.string.hypoglycemia)
                    } else {
                        text.text = getString(R.string.lowSugar)

                    }
                } else {
                    if (valueNoPressure > 140) {
                        text.text = getString(R.string.highSugarSenior)
                    } else if (valueNoPressure in 80.0..140.0) {
                        text.text = getString(R.string.normal_result)
                    } else if (valueNoPressure < 55) {
                        text.text = getString(R.string.hypoglycemia)
                    } else {
                        text.text = getString(R.string.lowSugar)
                    }
                }
            }
            "temperatura ciała" -> {
                if (yearsBetween < 11) {
                    if (valueNoPressure > 38.0) {
                        text.text = getString(R.string.fever)
                    } else if (valueNoPressure <= 38.0 && valueNoPressure > 37.5) {
                        text.text = getString(R.string.highTemp)
                    } else if (valueNoPressure in 35.5..37.5) {
                        text.text = getString(R.string.normal_result)
                    } else {
                        text.text = getString(R.string.lowTemp)
                    }
                } else if (yearsBetween > 65) {
                    if (valueNoPressure > 38.0) {
                        text.text = getString(R.string.fever)
                    } else if (valueNoPressure <= 38.0 && valueNoPressure > 36.9) {
                        text.text = getString(R.string.highTemp)
                    } else if (valueNoPressure in 35.8..36.9) {
                        text.text = getString(R.string.normal_result)
                    } else {
                        text.text = getString(R.string.highTemp)
                    }
                } else {
                    if (valueNoPressure > 38.0) {
                        text.text = getString(R.string.fever)
                    } else if (valueNoPressure <= 38.0 && valueNoPressure > 37.6) {
                        text.text = getString(R.string.highTemp)
                    } else if (valueNoPressure in 36.4..37.6) {
                        text.text = getString(R.string.normal_result)
                    } else {
                        text.text = getString(R.string.highTemp)
                    }
                }
            }
            "waga" -> {
                val bmi = valueNoPressure / Math.pow((height / 100), 2.0)
                if (bmi < 18.5) {
                    text.text = getString(R.string.underweight)
                } else if (bmi >= 18.5 && bmi < 25) {
                    text.text = getString(R.string.normal_result)
                } else if (bmi >= 25 && bmi < 30) {
                    text.text = getString(R.string.overweight)
                } else if (bmi >= 30 && bmi < 35) {
                    text.text = getString(R.string.obese1)
                } else if (bmi >= 35 && bmi < 40) {
                    text.text = getString(R.string.obese2)
                } else {
                    text.text = getString(R.string.obese3)
                }
            }

            else -> {
                if (yearsBetween < 1) {
                    if (valuePressure1 > 110 || valuePressure2 > 75) {
                        text.text = getString(R.string.highPressure)
                    } else if ((valuePressure1 in 75.0..110.0) && (valuePressure2 in 50.0..75.0)) {
                        text.text = getString(R.string.normal_result)
                    } else {
                        text.text = getString(R.string.lowPressure)
                    }
                } else if (yearsBetween in 1..5) {
                    if (valuePressure1 > 110 || valuePressure2 > 79) {
                        text.text = getString(R.string.highPressure)
                    } else if ((valuePressure1 in 80.0..110.0) && (valuePressure2 in 55.0..79.0)) {
                        text.text = getString(R.string.normal_result)
                    } else {
                        text.text = getString(R.string.lowPressure)
                    }
                } else if (yearsBetween in 6..13) {
                    if (valuePressure1 > 115 || valuePressure2 > 80) {
                        text.text = getString(R.string.highPressure)
                    } else if ((valuePressure1 in 90.0..115.0) && (valuePressure2 in 60.0..80.0)) {
                        text.text = getString(R.string.normal_result)
                    } else {
                        text.text = getString(R.string.lowPressure)
                    }
                } else if (yearsBetween in 14..19) {
                    if (valuePressure1 > 120 || valuePressure2 > 81) {
                        text.text = getString(R.string.highPressure)
                    } else if ((valuePressure1 in 105.0..120.0) && (valuePressure2 in 73.0..81.0)) {
                        text.text = getString(R.string.normal_result)
                    } else {
                        text.text = getString(R.string.lowPressure)
                    }
                } else if (yearsBetween in 20..24) {
                    if (valuePressure1 > 132 || valuePressure2 > 83) {
                        text.text = getString(R.string.highPressure)
                    } else if ((valuePressure1 in 108.0..132.0) && (valuePressure2 in 75.0..83.0)) {
                        text.text = getString(R.string.normal_result)
                    } else {
                        text.text = getString(R.string.lowPressure)
                    }
                } else if (yearsBetween in 25..29) {
                    if (valuePressure1 > 133 || valuePressure2 > 84) {
                        text.text = getString(R.string.highPressure)
                    } else if ((valuePressure1 in 109.0..133.0) && (valuePressure2 in 76.0..84.0)) {
                        text.text = getString(R.string.normal_result)
                    } else {
                        text.text = getString(R.string.lowPressure)
                    }
                } else if (yearsBetween in 30..34) {
                    if (valuePressure1 > 134 || valuePressure2 > 85) {
                        text.text = getString(R.string.highPressure)
                    } else if ((valuePressure1 in 110.0..134.0) && (valuePressure2 in 77.0..85.0)) {
                        text.text = getString(R.string.normal_result)
                    } else {
                        text.text = getString(R.string.lowPressure)
                    }
                } else if (yearsBetween in 35..39) {
                    if (valuePressure1 > 135 || valuePressure2 > 86) {
                        text.text = getString(R.string.highPressure)
                    } else if ((valuePressure1 in 111.0..135.0) && (valuePressure2 in 78.0..86.0)) {
                        text.text = getString(R.string.normal_result)
                    } else {
                        text.text = getString(R.string.lowPressure)
                    }
                } else if (yearsBetween in 40..44) {
                    if (valuePressure1 > 137 || valuePressure2 > 87) {
                        text.text = getString(R.string.highPressure)
                    } else if ((valuePressure1 in 112.0..137.0) && (valuePressure2 in 79.0..87.0)) {
                        text.text = getString(R.string.normal_result)
                    } else {
                        text.text = getString(R.string.lowPressure)
                    }
                } else if (yearsBetween in 45..49) {
                    if (valuePressure1 > 139 || valuePressure2 > 88) {
                        text.text = getString(R.string.highPressure)
                    } else if ((valuePressure1 in 115.0..139.0) && (valuePressure2 in 80.0..88.0)) {
                        text.text = getString(R.string.normal_result)
                    } else {
                        text.text = getString(R.string.lowPressure)
                    }
                } else if (yearsBetween in 50..54) {
                    if (valuePressure1 > 142 || valuePressure2 > 89) {
                        text.text = getString(R.string.highPressure)
                    } else if ((valuePressure1 in 116.0..142.0) && (valuePressure2 in 81.0..89.0)) {
                        text.text = getString(R.string.normal_result)
                    } else {
                        text.text = getString(R.string.lowPressure)
                    }
                } else if (yearsBetween in 55..59) {
                    if (valuePressure1 > 144 || valuePressure2 > 90) {
                        text.text = getString(R.string.highPressure)
                    } else if ((valuePressure1 in 118.0..144.0) && (valuePressure2 in 82.0..90.0)) {
                        text.text = getString(R.string.normal_result)
                    } else {
                        text.text = getString(R.string.lowPressure)
                    }
                } else {
                    if (valuePressure1 > 147 || valuePressure2 > 91) {
                        text.text = getString(R.string.highPressure)
                    } else if ((valuePressure1 in 121.0..147.0) && (valuePressure2 in 83.0..91.0)) {
                        text.text = getString(R.string.normal_result)
                    } else {
                        text.text = getString(R.string.lowPressure)
                    }
                }
            }
        }
    }
}