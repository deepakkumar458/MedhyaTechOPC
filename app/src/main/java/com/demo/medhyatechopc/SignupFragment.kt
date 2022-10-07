package com.demo.medhyatechopc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.demo.medhyatechopc.databinding.FragmentSignupBinding
import com.google.firebase.auth.FirebaseAuth


class SignupFragment : Fragment() {

    private lateinit var mBinding : FragmentSignupBinding
    private lateinit var firebaseAuth : FirebaseAuth

    private var mailOrPhone : String = ""
    private var password : String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }

        //Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentSignupBinding.inflate(inflater)
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.signinTv.setOnClickListener {
            findNavController().navigate(R.id.action_signupFragment_to_logInFragment)
        }

        //Check User Entered Details on Signup Button Click Listener
        mBinding.signUpBtn.setOnClickListener {
            checkDetails()
        }
    }

    private fun checkDetails() {
         mailOrPhone = mBinding.mailOrPhoneEt.text.toString()
         password = mBinding.passowrdEt.text.toString()
         val confirmPassword = mBinding.confirmPassowrdEt.text.toString()

        //Checking For user Details Entered is not Empty
        if (mailOrPhone.isEmpty()){
            Toast.makeText(requireContext() , "Please Enter Email or Phone.." , Toast.LENGTH_SHORT).show()
            return
        }
        if (password.isEmpty()){
            Toast.makeText(requireContext() , "Please Enter Password.." , Toast.LENGTH_SHORT).show()
            return
        }
        if (confirmPassword.isEmpty()){
            Toast.makeText(requireContext() , "Please Enter Confirm Password.." , Toast.LENGTH_SHORT).show()
            return
        }
        if (password != confirmPassword){
            Toast.makeText(requireContext() , "Please Enter Same Password.." , Toast.LENGTH_SHORT).show()
            return
        }

       firebaseSignUp()
    }

    private fun firebaseSignUp() {

        //For Create Account
        firebaseAuth.createUserWithEmailAndPassword(mailOrPhone , password)
            .addOnSuccessListener {
                //Sign Up Success
                Toast.makeText(requireContext() , "SignUp Success Please Login For Continue" , Toast.LENGTH_SHORT).show()

                //After Successfully Signup go back to Login Screen
                findNavController().navigate(R.id.action_signupFragment_to_logInFragment)

            }
                //Sign Up Failed
            .addOnFailureListener {
                Toast.makeText(requireContext() , "SignUp failed due to ${it.message}" , Toast.LENGTH_SHORT).show()
            }
    }

}