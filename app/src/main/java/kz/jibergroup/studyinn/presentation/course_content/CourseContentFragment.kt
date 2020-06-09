package kz.jibergroup.studyinn.presentation.course_content

import android.content.Context
import kz.jibergroup.studyinn.presentation.global.base.BaseFragment
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.course_content_fragment.*
import kz.jibergroup.studyinn.R
import kz.jibergroup.studyinn.domain.entity.LessonItem
import org.koin.androidx.viewmodel.ext.android.viewModel

class CourseContentFragment : BaseFragment() {

    companion object {
        private const val LESSON_CONTENT = "LESSON_CONTENT"

        fun newInstance(lessonItem: LessonItem): CourseContentFragment {
            val fragment = CourseContentFragment()
            val args = Bundle()
            args.putParcelable(LESSON_CONTENT, lessonItem)
            fragment.arguments = args
            return fragment
        }
    }

    override fun layoutId(): Int {
        return R.layout.course_content_fragment
    }

    val viewModel: CourseContentViewModel by viewModel()


    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getParcelable<LessonItem>(LESSON_CONTENT)?.let {
            viewModel.lessonItem.value = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.lessonItem.observe(viewLifecycleOwner, lessonObserver)
    }

    private val lessonObserver = Observer<LessonItem> {
        lessonTitle.text = it.title
        lessonContent.text = it.description
    }


}
