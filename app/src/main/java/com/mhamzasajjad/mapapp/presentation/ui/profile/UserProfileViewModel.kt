package com.mhamzasajjad.mapapp.presentation.ui.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhamzasajjad.mapapp.data.utils.Result
import com.mhamzasajjad.mapapp.domain.model.profile.UpdateUserProfileRequest
import com.mhamzasajjad.mapapp.domain.model.profile.UserProfile
import com.mhamzasajjad.mapapp.domain.usecases.UpdateUserProfileUseCase
import com.mhamzasajjad.mapapp.presentation.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val updateUserProfileUseCase: UpdateUserProfileUseCase
) : ViewModel() {

    private val _userProfileDetails = MutableStateFlow(
        UserProfile()
    )
    val userProfileDetails
        get() = _userProfileDetails.asStateFlow()

    private val _lastPickedPicture = MutableStateFlow<Uri?>(null)
    val lastPickedPicture
        get() = _lastPickedPicture.asStateFlow()

    private val _isSubscribed = MutableStateFlow(false)
    val isSubscribed
        get() = _isSubscribed.asStateFlow()

    private val _toastText = MutableStateFlow(Constants.DEFAULT_SUCCESS_MESSAGE)
    val toastText
        get() = _toastText.asStateFlow()

    private val _showToast = MutableStateFlow(false)
    val showToast
        get() = _showToast.asStateFlow()

    private val _showDatePicker = MutableStateFlow(false)
    val showDatePicker
        get() = _showDatePicker.asStateFlow()

    fun onProfilePictureChanged(imageUri: Uri) {
        _lastPickedPicture.value = imageUri
    }

    fun onFullNameChanged(fullName: String) {
        if (fullName.length > Constants.PROFILE_LONG_TEXT_FIELD_MAX_CHAR) {
            return
        }
        val parsedFullName = fullName.replace("\n", "")
        _userProfileDetails.value = _userProfileDetails.value.copy(fullName = parsedFullName)
    }

    fun setShowDatePicker(isShow: Boolean) {
        _showDatePicker.value = isShow
    }

    fun onBirthdayFieldClicked() {
        setShowDatePicker(true)
    }

    fun onBirthdayChanged(birthday: String) {
        if (birthday.length > Constants.PROFILE_SHORT_TEXT_FIELD_MAX_CHAR) {
            return
        }
        val parsedBirthday = birthday.replace("\n", "")
        _userProfileDetails.value = _userProfileDetails.value.copy(birthday = parsedBirthday)
    }

    fun onEmailChanged(email: String) {
        if (email.length > Constants.PROFILE_LONG_TEXT_FIELD_MAX_CHAR) {
            return
        }
        val parsedEmail = email.replace("\n", "")
        _userProfileDetails.value = _userProfileDetails.value.copy(email = parsedEmail)
    }

    fun onPhoneChanged(phone: String) {
        if (phone.length > Constants.PROFILE_SHORT_TEXT_FIELD_MAX_CHAR) {
            return
        }
        val parsedPhone = phone.replace("\n", "")
        _userProfileDetails.value = _userProfileDetails.value.copy(phone = parsedPhone)
    }

    fun onSubscribedChanged() {
        _isSubscribed.value = !isSubscribed.value
    }

    fun onSaveChangesClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            updateUserProfileUseCase.invoke(
                updateUserProfileRequest = UpdateUserProfileRequest(
                    userId = Constants.PLACEHOLDER_USER_ID,
                    fullName = _userProfileDetails.value.fullName,
                    birthday = _userProfileDetails.value.birthday,
                    email = _userProfileDetails.value.email,
                    phone = _userProfileDetails.value.phone,
                    isSubscribed = _isSubscribed.value
                )
            ).collectLatest {
                when (it) {
                    is Result.Success -> {
                        _toastText.value = it.data.message ?: ""
                        _showToast.value = true
                    }

                    is Result.Error -> {
                        _toastText.value = it.throwable.errorMessage ?: ""
                        _showToast.value = true
                    }
                }
            }
        }
        if (!_showToast.value && _toastText.value != "") {
            _showToast.value = true
        }
    }

    fun hideToast() {
        _showToast.value = false
    }
}