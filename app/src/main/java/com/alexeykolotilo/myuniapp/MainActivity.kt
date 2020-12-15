package com.alexeykolotilo.myuniapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import com.alexeykolotilo.myuniapp.adapter.RecAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_recycler.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private val textFields = arrayListOf("1", "2")

    private val adapter = RecAdapter().apply { submitList(textFields) }

    override fun onStart() {
        super.onStart()
        button.setOnClickListener {
            Intent(this, AnotherActivity::class.java).also { intent ->
                intent.putExtras(
                    bundleOf("myText" to textInput.text.toString())
                )
                startActivity(intent, bundleOf())
            }
        }

        recycler.adapter = adapter
        add.setOnClickListener {
            textFields.add(textFields.size.toString())
            onListChanged()
        }
        swap.setOnClickListener {
            if (textFields.size < 2) return@setOnClickListener
            val first = textFields[textFields.lastIndex - 1]
            val second = textFields[textFields.lastIndex]

            textFields[textFields.lastIndex - 1] = second
            textFields[textFields.lastIndex] = first
            onListChanged()
        }

        remove.setOnClickListener {
            textFields.removeLastOrNull()
            onListChanged()
        }

        focusFirst.setOnClickListener {
            adapter.notifyItemChanged(0, RecAdapter.FieldAction.FOCUS)
        }
        var f = false

        changeTypeFace.setOnClickListener {
            if (f) {
                adapter.notifyItemChanged(0, RecAdapter.FieldAction.TYPEFACE_LIGHT)
            } else {
                adapter.notifyItemChanged(0, RecAdapter.FieldAction.TYPEFACE_NORMAL)
            }
            f = !f
        }
    }

    private fun onListChanged() {
        adapter.submitList(textFields.toList())
    }
}