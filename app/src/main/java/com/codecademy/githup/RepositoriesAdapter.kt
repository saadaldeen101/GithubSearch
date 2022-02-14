package com.codecademy.githup

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.codecademy.githup.network.Repository

class RepositoriesAdapter: RecyclerView.Adapter<RepositoriesAdapter.ViewHolder>() {

    var repositories: List<Repository> = emptyList()

    var onItemClicked: (Repository) -> Unit = {}

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val avatarImageView: ImageView = itemView.findViewById(R.id.userAvatar)
        val ropoNameTextView: TextView = itemView.findViewById(R.id.repoName)
        val userNameTextView: TextView = itemView.findViewById(R.id.userName)
    }

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.repository_recyclerview_item, parent, false)
        return ViewHolder(view)
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = repositories[position]
        holder.avatarImageView.load(item.owner.avatar)
        holder.ropoNameTextView.text = item.name
        holder.userNameTextView.text = item.owner.login
        holder.itemView.setOnClickListener {
            onItemClicked(repositories[position])
        }
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount(): Int {
        return repositories.size
    }


}