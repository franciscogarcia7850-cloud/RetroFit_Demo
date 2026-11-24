package com.example.retrofit_demo.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.retrofit_demo.R
import com.example.retrofit_demo.data.Post

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostListScreen(viewModel: PostViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text(stringResource(R.string.posts_title)) }) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when (val state = uiState) {
                PostUiState.Loading -> CircularProgressIndicator()
                PostUiState.Error -> ErrorMessage(onRetry = { viewModel.loadPosts() })
                is PostUiState.Success ->
                    if (state.posts.isEmpty()) Text(stringResource(R.string.empty))
                    else PostList(posts = state.posts)
            }
        }
    }
}

@Composable
private fun PostList(posts: List<Post>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(posts, key = { it.id }) { post ->
            PostItem(post = post)
        }
    }
}

@Composable
private fun PostItem(post: Post) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = stringResource(R.string.post_info, post.id, post.userId),
                style = MaterialTheme.typography.labelMedium
            )
            Text(text = post.title, style = MaterialTheme.typography.titleMedium)
            Text(text = post.body, style = MaterialTheme.typography.bodyMedium)
            Text(
                text = post.link,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = stringResource(R.string.comments, post.commentCount),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Composable
private fun ErrorMessage(onRetry: () -> Unit) {
    Column(
        modifier = Modifier.padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.loading_error), textAlign = TextAlign.Center)
        Button(onClick = onRetry) { Text(stringResource(R.string.retry)) }
    }
}
