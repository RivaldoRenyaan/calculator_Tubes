package org.d3if2042.calculator.ui.tentang

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import org.d3if2042.calculator.R
import org.d3if2042.calculator.databinding.KalkulasiFragmentBinding
import org.d3if2042.calculator.databinding.TentangFragmentBinding
import org.d3if2042.calculator.model.TentangAplikasi
import org.d3if2042.calculator.network.ApiStatus
import org.d3if2042.calculator.network.ApiTentang

class TentangFragment : Fragment(){
    private val viewModel: TentangViewModel by lazy {
        val factory = TentangViewModelFactory(requireActivity().application)
        ViewModelProvider(this, factory)[TentangViewModel::class.java]
    }

    private lateinit var binding: TentangFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TentangFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getData().observe(viewLifecycleOwner) {
            getData(it)
        }

        viewModel.getStatus().observe(viewLifecycleOwner) {
            getStatusApi(it)
        }

        Glide.with(binding.imageView)
            .load(ApiTentang.getGambarUrl())
            .error(R.drawable.ic_baseline_broken_image_24)
            .into(binding.imageView)
    }

    private fun getStatusApi(it: ApiStatus?) {
        when (it) {
            ApiStatus.LOADING -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            ApiStatus.SUCCESS -> {
                binding.progressBar.visibility = View.GONE
            }
            ApiStatus.FAILED -> {
                binding.progressBar.visibility = View.GONE
                binding.networkError.visibility = View.VISIBLE
            }
        }

    }

    private fun getData(it : TentangAplikasi?) {
        if (it != null) {
            binding.tentangText.text = it.tentang
        }
    }


}