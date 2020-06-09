package kz.jibergroup.studyinn.presentation.modules

import android.ibanking.altyn.presentation.global.base.BaseViewModel
import androidx.lifecycle.MutableLiveData
import kz.jibergroup.studyinn.data.repo.CourseRepository
import kz.jibergroup.studyinn.domain.entity.ModuleItem

class CourseModulesViewModel(
    private val courseRepository: CourseRepository
) : BaseViewModel() {

    val modulesServer = MutableLiveData<MutableList<ModuleItem>>()
    val modules =
        MutableLiveData<MutableList<kz.jibergroup.studyinn.presentation.modules.ModuleItem>>()

    fun getModules(id: Int) {
        makeRequest({ courseRepository.getAllModulesByCourseId(id) }) {
            unwrap(it) {
                modulesServer.value = it.data
                mapModules()
            }
        }

    }

    fun mapModules() {
        val data = mutableListOf<kz.jibergroup.studyinn.presentation.modules.ModuleItem>()

        modulesServer.value?.forEach {

            val lessons = mutableListOf<ModuleChildItem>()

            it.lessons?.forEach {
                lessons.add(ModuleChildItem(id = it.id, title = it.title))
            }

            data.add(
                ModuleItem(
                    id = it.id,
                    titleParent = it.title,
                    modules = lessons
                )
            )

        }

        modules.value = data


    }

}
