package com.example.cp4kotlin.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.cp4kotlin.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class GalleryFragment : Fragment() {

    private lateinit var linearLayoutTarefas: LinearLayout
    private val listaTarefas = mutableListOf<String>() // Lista simples para as tarefas

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate o layout do fragmento
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Referenciar os componentes do layout
        linearLayoutTarefas = view.findViewById(R.id.linearLayoutTarefas)
        val fabAddTarefa = view.findViewById<FloatingActionButton>(R.id.fabAddTarefa)

        // Listener para o botão flutuante
        fabAddTarefa.setOnClickListener {
            mostrarDialogoAdicionarTarefa()
        }
    }

    // Função para mostrar o diálogo e permitir a inserção do nome da tarefa
    private fun mostrarDialogoAdicionarTarefa() {
        // Criar um campo EditText para o usuário inserir o nome da tarefa
        val editText = EditText(requireContext()).apply {
            hint = "Digite o nome da tarefa"
            setPadding(16, 16, 16, 16)
        }

        // Criar o diálogo de alerta
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Adicionar Tarefa")
            setMessage("Insira o nome da tarefa que deseja adicionar")
            setView(editText)

            // Botão de adicionar tarefa
            setPositiveButton("Adicionar") { _, _ ->
                val nomeTarefa = editText.text.toString()
                if (nomeTarefa.isNotEmpty()) {
                    adicionarTarefa(nomeTarefa)
                } else {
                    Toast.makeText(requireContext(), "O nome da tarefa não pode estar vazio", Toast.LENGTH_SHORT).show()
                }
            }

            // Botão de cancelar
            setNegativeButton("Cancelar", null)
        }.create().show()
    }

    // Função para adicionar a tarefa ao layout
    private fun adicionarTarefa(nomeTarefa: String) {
        // Criar um novo layout horizontal para a tarefa e o botão de excluir
        val tarefaLayout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(16, 16, 16, 16)
        }

        // Criar um TextView para o nome da tarefa
        val novaTarefa = TextView(requireContext()).apply {
            text = nomeTarefa
            textSize = 16f
            setPadding(16, 16, 16, 16)
        }

        // Criar um botão para excluir a tarefa
        val botaoExcluir = Button(requireContext()).apply {
            text = "Excluir"
            setOnClickListener {
                removerTarefa(tarefaLayout, nomeTarefa)
            }
        }

        // Adicionar o TextView e o botão ao layout da tarefa
        tarefaLayout.addView(novaTarefa)
        tarefaLayout.addView(botaoExcluir)

        // Adicionar o layout da tarefa ao LinearLayout principal
        linearLayoutTarefas.addView(tarefaLayout)

        // Adicionar a tarefa na lista
        listaTarefas.add(nomeTarefa)

        // Notificar o usuário
        Toast.makeText(requireContext(), "Tarefa '$nomeTarefa' adicionada", Toast.LENGTH_SHORT).show()
    }

    // Função para remover a tarefa
    private fun removerTarefa(tarefaLayout: LinearLayout, nomeTarefa: String) {
        // Remover o layout da tarefa da tela
        linearLayoutTarefas.removeView(tarefaLayout)

        // Remover a tarefa da lista
        listaTarefas.remove(nomeTarefa)

        // Notificar o usuário
        Toast.makeText(requireContext(), "Tarefa '$nomeTarefa' removida", Toast.LENGTH_SHORT).show()
    }
}
