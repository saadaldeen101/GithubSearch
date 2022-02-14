package com.codecademy.githup

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import coil.load
import org.w3c.dom.Text


class RepositoryDetailFragment : Fragment() {

    private val viewModel: RepositoryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_repository_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val avatarImageView: ImageView = view.findViewById(R.id.avatar)
        val repositoryNameTextView: TextView = view.findViewById(R.id.repositoryName)
        val ownerNameTextView: TextView = view.findViewById(R.id.ownerName)
        val emailTextView: TextView = view.findViewById(R.id.emailTextView)
        val forkTextView: TextView = view.findViewById(R.id.forkTextView)
        val branchTextView: TextView = view.findViewById(R.id.branchTextView)
        val languageTextView: TextView = view.findViewById(R.id.languageTextView)
        viewModel.selectedRepository?.let {
            avatarImageView.load(it.owner.avatar)
            repositoryNameTextView.text = it.name
            ownerNameTextView.text = it.owner.login
            emailTextView.text = it.owner.email
            forkTextView.text = it.forks.toString()
            branchTextView.text = it.defaultBranch
            languageTextView.text = it.language
        }
    }
}