package com.example.myfirstapp.di

import kz.jibergroup.studyinn.data.repo.CourseRepository
import kz.jibergroup.studyinn.data.repo.PersonalRepository
import kz.jibergroup.studyinn.presentation.auth.login.LoginViewModel
import kz.jibergroup.studyinn.presentation.auth.registration.RegisterViewModel
import kz.jibergroup.studyinn.presentation.catalog.CatalogViewModel
import kz.jibergroup.studyinn.presentation.category_courses.CategoryCoursesViewModel
import kz.jibergroup.studyinn.presentation.change_email.ChangeEmailViewModel
import kz.jibergroup.studyinn.presentation.change_name.EditProfileViewModel
import kz.jibergroup.studyinn.presentation.change_password.ChangePasswordViewModel
import kz.jibergroup.studyinn.presentation.course_content.CourseContentViewModel
import kz.jibergroup.studyinn.presentation.course_detail.CourseDetailViewModel
import kz.jibergroup.studyinn.presentation.course_learning.CourseLearningViewModel
import kz.jibergroup.studyinn.presentation.course_review.CourseReviewViewModel
import kz.jibergroup.studyinn.presentation.course_test.CourseTestViewModel
import kz.jibergroup.studyinn.presentation.course_video.CourseVideoViewModel
import kz.jibergroup.studyinn.presentation.courseinfo.CourseInfoViewModel
import kz.jibergroup.studyinn.presentation.home.HomeViewModel
import kz.jibergroup.studyinn.presentation.image_dialog.GalleryDialogViewModel
import kz.jibergroup.studyinn.presentation.main.MainViewModel
import kz.jibergroup.studyinn.presentation.modules.CourseModulesViewModel
import kz.jibergroup.studyinn.presentation.my_courses.MyCoursesViewModel
import kz.jibergroup.studyinn.presentation.profile.ProfileViewModel
import kz.jibergroup.studyinn.presentation.reset_user.ResetUserViewModel
import kz.jibergroup.studyinn.presentation.review_dialog.ReviewDialogViewModel
import kz.jibergroup.studyinn.presentation.settings.SettingsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {

    single {
        PersonalRepository(androidContext(), get(), get())
    }
    single {
        CourseRepository(androidContext(), get(), get())
    }
    viewModel {
        HomeViewModel(get(), get())
    }
    viewModel {
        CatalogViewModel(get())
    }
    viewModel {
        CourseReviewViewModel(get(), get())
    }
    viewModel {
        CourseModulesViewModel(get())
    }
    viewModel {
        MyCoursesViewModel(get())
    }
    viewModel {
        LoginViewModel(get())
    }
    viewModel {
        CourseDetailViewModel(get())
    }
    viewModel {
        CourseLearningViewModel(get())
    }
    viewModel {
        CategoryCoursesViewModel(get())
    }
    viewModel {
        CourseInfoViewModel()
    }
    viewModel {
        SettingsViewModel(get())
    }
    viewModel {
        MainViewModel(get())
    }
    viewModel {
        RegisterViewModel(get())
    }
    viewModel {
        CourseContentViewModel()
    }
    viewModel {
        CourseTestViewModel()
    }
    viewModel {
        CourseVideoViewModel()
    }
    viewModel {
        ProfileViewModel(get())
    }
    viewModel {
        ChangePasswordViewModel(get())
    }
    viewModel {
        ChangeEmailViewModel(get())
    }
    viewModel {
        ResetUserViewModel(get())
    }
    viewModel {
        GalleryDialogViewModel()
    }
    viewModel {
        EditProfileViewModel(get())
    }
    viewModel {
        ReviewDialogViewModel(get(),get())
    }


}