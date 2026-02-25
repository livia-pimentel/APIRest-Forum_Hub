
ALTER TABLE cursos ADD CONSTRAINT UC_Curso_Nome UNIQUE (nome);

ALTER TABLE usuarios ADD CONSTRAINT UC_Usuario_Nome UNIQUE (nome);
