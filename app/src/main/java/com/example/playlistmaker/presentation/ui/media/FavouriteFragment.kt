
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.playlistmaker.databinding.FragmentFavouriteBinding
import com.example.playlistmaker.databinding.FragmentPlaylistBinding

class FavouriteFragment : Fragment() {

    companion object {

        fun newInstance(number: Int) = FavouriteFragment().apply {
            arguments = Bundle().apply {
            }
        }
    }

    private lateinit var binding: FragmentFavouriteBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}