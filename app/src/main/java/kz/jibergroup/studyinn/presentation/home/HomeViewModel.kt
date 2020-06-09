package kz.jibergroup.studyinn.presentation.home

import android.ibanking.altyn.presentation.global.base.BaseViewModel
import androidx.lifecycle.MutableLiveData
import kz.jibergroup.studyinn.data.repo.CourseRepository
import kz.jibergroup.studyinn.data.repo.PersonalRepository
import kz.jibergroup.studyinn.domain.entity.PostItem
import kz.jibergroup.studyinn.domain.entity.Teacher
import kz.jibergroup.studyinn.presentation.home.player.MediaObject

class HomeViewModel(
    private val personalRepository: PersonalRepository,
    private val courseRepository: CourseRepository
) : BaseViewModel() {

    val posts = MutableLiveData<MutableList<PostItem>>()
    val mediaobjects = MutableLiveData<ArrayList<PostItem>>()

    fun getPost() {

//        personalRepository.saveAccessToken(
//            "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Imt1YW55c2guYnlAZ21haWwuY29tIiwiaWQiOjksImlhdCI6MTU4NzU2NjMwNSwiZXhwIjoxNTkyODEyMjk1fQ.MGzKYUmPmwV3YKABRiLTZja3_OzHkqddO4oZh1CFzGs"
//        )
        makeRequest({ courseRepository.getPosts() }) {
            unwrap(it) {
                posts.value = it.data
            }
        }

    }


    fun initPost() {
        val data = arrayListOf<PostItem>()

        val mediaItem = MediaObject(
            title = "Sending Data to a New Activity with Intent Extras",
            media_url = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
            thumbnail = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
        )

        data.add(
            PostItem(
                title = "Top 55 university",
                description = "fnaenalkgnlkaegnkleg"
                , teacher = Teacher(last_name = "Abdinazar", first_name = "Mukhit"),
                mediaObject = mediaItem
            )
        )

        data.add(
            PostItem(
                title = "Top 55 university",
                description = "fnaenalkgnlkaegnkleg"
                , teacher = Teacher(last_name = "Abdinazar", first_name = "Mukhit"),
                mediaObject = mediaItem
            )
        )
        data.add(
            PostItem(
                title = "Top 55 university",
                description = "fnaenalkgnlkaegnkleg"
                , teacher = Teacher(last_name = "Abdinazar", first_name = "Mukhit"),
                mediaObject = mediaItem
            )
        )
        data.add(
            PostItem(
                title = "Top 55 university",
                description = "fnaenalkgnlkaegnkleg"
                , teacher = Teacher(last_name = "Abdinazar", first_name = "Mukhit"),
                mediaObject = mediaItem
            )
        )
        data.add(
            PostItem(
                title = "Top 55 university",
                description = "fnaenalkgnlkaegnkleg"
                , teacher = Teacher(last_name = "Abdinazar", first_name = "Mukhit"),
                mediaObject = mediaItem
            )
        )
        data.add(
            PostItem(
                title = "Top 55 university",
                description = "fnaenalkgnlkaegnkleg"
                , teacher = Teacher(last_name = "Abdinazar", first_name = "Mukhit"),
                mediaObject = mediaItem
            )
        )
        data.add(
            PostItem(
                title = "Top 55 university",
                description = "fnaenalkgnlkaegnkleg"
                , teacher = Teacher(last_name = "Abdinazar", first_name = "Mukhit"),
                mediaObject = mediaItem
            )
        )


        mediaobjects.value = data

    }

    enum class Error {
        Not_found, ServerError, Success
    }

}
