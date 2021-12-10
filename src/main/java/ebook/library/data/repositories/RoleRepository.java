package ebook.library.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ebook.library.data.entity.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer>{

}
