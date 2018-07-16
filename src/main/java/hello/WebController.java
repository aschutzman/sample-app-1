package hello;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import org.thatscloud.pubj.Pubg;
import org.thatscloud.pubj.rest.model.Player;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Controller
public class WebController implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/results").setViewName("results");
	}

	@GetMapping("/")
	public String showForm(Model model) {
		PersonForm form = new PersonForm();
		model.addAttribute("personForm", form);

		List<Region> list = new ArrayList<Region>();
		Region jp = new Region();
		jp.setDisplayName("Japan");
		jp.setName("jp");
		Region na = new Region();
		na.setDisplayName("North America");
		na.setName("na");
		list.add(jp);
		list.add(na);
	
		model.addAttribute("regions", list);

		return "form";
	}

	@PostMapping("/")
	public String checkPersonInfo(@Valid PersonForm personForm, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "form";
		}

		return "redirect:/results";
	}

	private void getPUBGData(String playerName) {
		final String apiKey = "MY-TRN-API-KEY";
		try (final Pubg pubg = new Pubg(apiKey)) {
			final Player player = pubg.getPlayer(playerName);
		}
	}
}
