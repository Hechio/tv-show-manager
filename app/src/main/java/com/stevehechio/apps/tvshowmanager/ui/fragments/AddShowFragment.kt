package com.stevehechio.apps.tvshowmanager.ui.fragments

import android.app.Activity
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.apollographql.apollo.api.Input
import com.stevehechio.apps.tvshowmanager.R
import com.stevehechio.apps.tvshowmanager.data.Resource
import com.stevehechio.apps.tvshowmanager.databinding.FragmentAddShowBinding
import com.stevehechio.apps.tvshowmanager.type.CreateMovieFieldsInput
import com.stevehechio.apps.tvshowmanager.type.CreateMovieInput
import com.stevehechio.apps.tvshowmanager.ui.viewmodels.AddShowViewModel
import com.stevehechio.apps.tvshowmanager.utils.DateUtils
import com.stevehechio.apps.tvshowmanager.utils.gone
import com.stevehechio.apps.tvshowmanager.utils.visible
import dagger.android.support.AndroidSupportInjection
import java.util.*
import javax.inject.Inject


class AddShowFragment : Fragment() {
    private var _binding: FragmentAddShowBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var addShowViewModel: AddShowViewModel

    private var cal: Calendar = Calendar.getInstance()
    private val dateSetListener =
        DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateInEditText()
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddShowBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AndroidSupportInjection.inject(this)
        setUpReleaseDateEditText()
        addShowViewModel = ViewModelProviders.of(this,viewModelFactory)
            .get(AddShowViewModel::class.java)
        binding.btnSaveShow.setOnClickListener { onSubmitMovie() }
    }

    private fun onSubmitMovie() {
        val releaseDate: String? = binding.tlReleaseDate.editText?.text?.toString()
        val title = binding.tlTvShow.editText?.text?.toString()
        val seasons = binding.tlSeasons.editText?.text?.toString()
        if (title.isNullOrEmpty()){
            binding.tlTvShow.error = "enter the tv show title"
            return
        }
        if (releaseDate.isNullOrEmpty()){
            binding.tlReleaseDate.error = "select release date"
            return
        }

         if (seasons.isNullOrEmpty()){
            binding.tlSeasons.error = "enter number of seasons"
            return
        }

        val input = CreateMovieInput(fields = Input.fromNullable(CreateMovieFieldsInput(title = title,
            releaseDate = Input.fromNullable(DateUtils.formatStrDateToGMTString(cal.time)),
            seasons = Input.fromNullable(seasons.toDouble()))))

        createNewMovies(input)
    }

    private fun createNewMovies(input: CreateMovieInput) {
        addShowViewModel.createMovies(input).observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    stopLoadingWithSucess()
                }
                is Resource.Loading -> {
                    startLoading()
                }
                else -> {
                    binding.tvError.text = getString(R.string.something_went_wrong)
                    stopLoadingWithError()
                }
            }
        })
    }

    private fun stopLoadingWithError() {
        binding.pbShows.gone()

    }

    private fun startLoading() {
        binding.pbShows.visible()
    }

    private fun stopLoadingWithSucess() {
        binding.pbShows.gone()
        findNavController().popBackStack()
    }

    private fun setUpReleaseDateEditText() {
        binding.tlReleaseDate.editText?.onFocusChangeListener =
            View.OnFocusChangeListener { _, hasFocus ->
                hideKeyboard()
                if (hasFocus){
                    showDatePickerDialog()
                }
            }
    }

    private fun showDatePickerDialog() {
        DatePickerDialog(requireContext(),dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun updateDateInEditText() {
        binding.tlReleaseDate.editText?.setText(DateUtils.formatStrDateToGMTStringForUser(cal.time))
    }

    private fun hideKeyboard() {
        binding.tlReleaseDate.editText?.setRawInputType(InputType.TYPE_CLASS_TEXT)
        binding.tlReleaseDate.editText?.setTextIsSelectable(true)
        binding.tlReleaseDate.editText?.showSoftInputOnFocus = false
        onHideKeyboard(requireActivity())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun onHideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}