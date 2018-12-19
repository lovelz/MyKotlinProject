package com.lovelz.mykotlinproject.model.bean


import com.google.gson.annotations.SerializedName
import java.util.*

open class RepoCommit {

    var sha: String? = null
    var url: String? = null
    @SerializedName("html_url")
    var htmlUrl: String? = null
    @SerializedName("comments_url")
    var commentsUrl: String? = null

    var commit: CommitGitInfo? = null
    var author: User? = null
    var committer: User? = null
    var parents: ArrayList<RepoCommit>? = null
}
