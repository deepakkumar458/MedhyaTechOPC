package com.demo.medhyatechopc

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.demo.medhyatechopc.databinding.FragmentLogInBinding
import com.google.firebase.auth.FirebaseAuth


class LogInFragment : Fragment() {

    private lateinit var mBinding : FragmentLogInBinding
    private lateinit var firebaseAuth : FirebaseAuth

    private var mailOrPhone : String = ""
    private var password : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentLogInBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()

        //For Check User Details on Login Button Clicked
        mBinding.signInBtn.setOnClickListener {
            checkUserDetails()
        }

        //Set On Click Listener on SignUp Text View
        mBinding.signUpTv.setOnClickListener {
            findNavController().navigate(R.id.action_logInFragment_to_signupFragment)
        }
    }

    //For Check User Details
    private fun checkUserDetails() {
         mailOrPhone = mBinding.mailOrPhoneEt.text.toString()
         password = mBinding.passowrdEt.text.toString()

        //For Checking Enter User Mail or Phone is not empty
        if (mailOrPhone.isEmpty()){
            Toast.makeText(requireContext() , "Please Enter Email or Phone.." , Toast.LENGTH_SHORT).show()
            return
        }
        if (password.isEmpty()){
            Toast.makeText(requireContext() , "Please Enter Password.." , Toast.LENGTH_SHORT).show()
            return
        }

        //Function for Firabase Login
        firebaseLogin()

    }

    private fun firebaseLogin() {
        firebaseAuth.signInWithEmailAndPassword(mailOrPhone , password)
            .addOnSuccessListener {
                //login success
                //After successfully Login go to home screen
                findNavController().navigate(R.id.action_logInFragment_to_homeFragment)
            }
            .addOnFailureListener {
                //login Failed
                Toast.makeText(requireContext() , "login failed due to ${it.message}" , Toast.LENGTH_SHORT).show()
            }
    }


}