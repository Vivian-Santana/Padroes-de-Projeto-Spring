package one.digitalinovation.gof.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import lombok.RequiredArgsConstructor;
import one.digitalinovation.gof.entity.Cliente;
import one.digitalinovation.gof.repository.ClienteRepository;

@Service
@RequiredArgsConstructor
public class ClienteService {

	private final ClienteRepository clienteRepository;
	
	public Cliente save(Cliente cliente) {
		RestTemplate restTemplate = new RestTemplate();
		Cliente cliente1;
		String uri = "https://viacep.com.br/ws/"+cliente.getCep()+"/json/";
		cliente1 = restTemplate.getForObject(uri, Cliente.class);
		cliente.setBairro(cliente.getBairro());
		cliente1.setNome(cliente.getNome());
		cliente1.setCep(cliente.getCep());
		
		return clienteRepository.save(cliente1);
	}
	
	public Cliente getCep(String cep) {
		RestTemplate restTemplate = new RestTemplate();
		Cliente cliente1;
		String uri = "https://viacep.com.br/ws/"+cep+"/json/";
		cliente1 = restTemplate.getForObject(uri, Cliente.class);
		return cliente1;
	}
}
