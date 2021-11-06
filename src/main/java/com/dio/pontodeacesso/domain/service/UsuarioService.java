package com.dio.pontodeacesso.domain.service;

import com.dio.pontodeacesso.domain.exception.EntidadeEmUsoException;
import com.dio.pontodeacesso.domain.exception.UsuarioNaoEncontradoException;
import com.dio.pontodeacesso.domain.model.Usuario;
import com.dio.pontodeacesso.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final CategoriaUsuarioService categoriaUsuarioService;
    private final EmpresaService empresaService;
    private final NivelAcessoService nivelAcessoService;
    private final JornadaService jornadaService;


    @Transactional
    public Usuario save(Usuario usuario) {
        categoriaUsuarioService.findById(usuario.getCategoriaUsuario().getId_cat_usuario());
        empresaService.findById(usuario.getEmpresa().getId_empresa());
        nivelAcessoService.findById(usuario.getNivelAcesso().getId_nivelAcesso());
        jornadaService.findById(usuario.getJornadaTrabalho().getId_jornada_trabalho());
        return usuarioRepository.save(usuario);
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElseThrow(
                ()-> new UsuarioNaoEncontradoException(id)
        );
    }

    @Transactional
    public void delete(Long id) {
        try {
            usuarioRepository.deleteById(id);
            usuarioRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new UsuarioNaoEncontradoException(id);
        } catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(
                    String.format("O Usuário com id %d está em uso e não pode ser excluido", id));
        }
    }

    public List<Usuario> findAll() {
        return  usuarioRepository.findAll();
    }
}
