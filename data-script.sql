INSERT INTO
    especialidad (
        nombre,
        descripcion,
        icon_class,
        costo
    )
values (
        'Medicina General',
        'Consultas generales y diagnóstico inicial',
        'bi bi-shield-plus',
        80
    ),
    (
        'Cardiolgía',
        'Salud del corazón y sistema circulatorio',
        'bi bi-heart-pulse',
        150
    ),
    (
        'Pediatría',
        'Atención médica integral para niños',
        'bi bi-emoji-smile',
        120
    ),
    (
        'Neurología',
        'Estudio y tratamiento del sistema nervioso',
        'bi bi-cpu',
        100
    );

-- Usuarios de médicos de ejemplo
INSERT IGNORE INTO
    Usuario (
        nombres,
        apellidos,
        correo,
        contraseña,
        id_rol,
        estado,
        fecha_registro
    )
VALUES (
        'Roberto',
        'Méndez',
        'roberto.mendez@medisys.com',
        'Doctor123!',
        (
            SELECT id_rol
            FROM Rol
            WHERE
                nombre = 'MEDICO'
        ),
        true,
        NOW()
    ),
    (
        'Elena',
        'Salazar',
        'elena.salazar@medisys.com',
        'Doctor123!',
        (
            SELECT id_rol
            FROM Rol
            WHERE
                nombre = 'MEDICO'
        ),
        true,
        NOW()
    ),
    (
        'Carlos',
        'Villanueva',
        'carlos.villanueva@medisys.com',
        'Doctor123!',
        (
            SELECT id_rol
            FROM Rol
            WHERE
                nombre = 'MEDICO'
        ),
        true,
        NOW()
    ),
    (
        'Lucía',
        'Ortiz',
        'lucia.ortiz@medisys.com',
        'Doctor123!',
        (
            SELECT id_rol
            FROM Rol
            WHERE
                nombre = 'MEDICO'
        ),
        true,
        NOW()
    ),
    (
        'Ana',
        'Gómez',
        'ana.gomez@medisys.com',
        'Doctor123!',
        (
            SELECT id_rol
            FROM Rol
            WHERE
                nombre = 'MEDICO'
        ),
        true,
        NOW()
    ),
    (
        'Luis',
        'Ramírez',
        'luis.ramirez@medisys.com',
        'Doctor123!',
        (
            SELECT id_rol
            FROM Rol
            WHERE
                nombre = 'MEDICO'
        ),
        true,
        NOW()
    ),
    (
        'María',
        'Páez',
        'maria.paez@medisys.com',
        'Doctor123!',
        (
            SELECT id_rol
            FROM Rol
            WHERE
                nombre = 'MEDICO'
        ),
        true,
        NOW()
    ),
    (
        'Luis',
        'Cruz',
        'luis.cruz@medisys.com',
        'Doctor123!',
        (
            SELECT id_rol
            FROM Rol
            WHERE
                nombre = 'MEDICO'
        ),
        true,
        NOW()
    );

-- Médicos de ejemplo por especialidad
INSERT IGNORE INTO
    Medico (
        id_usuario,
        id_Especialidad,
        numero_colegiatura,
        telefono,
        anios_experiencia,
        foto_url,
        valoracion,
        disponibilidad
    )
VALUES (
        (
            SELECT id_usuario
            FROM Usuario
            WHERE
                correo = 'roberto.mendez@medisys.com'
        ),
        (
            SELECT id_Especialidad
            FROM Especialidad
            WHERE
                nombre = 'Medicina General'
        ),
        '52341',
        '999888111',
        12,
        'https://i.pravatar.cc/56?img=11',
        4.9,
        'DISPONIBLE HOY'
    ),
    (
        (
            SELECT id_usuario
            FROM Usuario
            WHERE
                correo = 'elena.salazar@medisys.com'
        ),
        (
            SELECT id_especialidad
            FROM Especialidad
            WHERE
                nombre = 'Medicina General'
        ),
        '61892',
        '999888222',
        8,
        'https://i.pravatar.cc/56?img=5',
        4.8,
        'MAÑANA: 08:00 AM'
    ),
    (
        (
            SELECT id_usuario
            FROM Usuario
            WHERE
                correo = 'carlos.villanueva@medisys.com'
        ),
        (
            SELECT id_especialidad
            FROM Especialidad
            WHERE
                nombre = 'Cardiolgía'
        ),
        '44210',
        '999888333',
        15,
        'https://i.pravatar.cc/56?img=15',
        4.7,
        'DISPONIBLE HOY'
    ),
    (
        (
            SELECT id_usuario
            FROM Usuario
            WHERE
                correo = 'lucia.ortiz@medisys.com'
        ),
        (
            SELECT id_especialidad
            FROM Especialidad
            WHERE
                nombre = 'Cardiolgía'
        ),
        '73509',
        '999888444',
        6,
        'https://i.pravatar.cc/56?img=9',
        5.0,
        'MAÑANA: 10:30 AM'
    ),
    (
        (
            SELECT id_usuario
            FROM Usuario
            WHERE
                correo = 'ana.gomez@medisys.com'
        ),
        (
            SELECT id_especialidad
            FROM Especialidad
            WHERE
                nombre = 'Pediatría'
        ),
        '38875',
        '999888555',
        10,
        'https://i.pravatar.cc/56?img=21',
        4.6,
        'DISPONIBLE HOY'
    ),
    (
        (
            SELECT id_usuario
            FROM Usuario
            WHERE
                correo = 'luis.ramirez@medisys.com'
        ),
        (
            SELECT id_especialidad
            FROM Especialidad
            WHERE
                nombre = 'Pediatría'
        ),
        '55944',
        '999888666',
        7,
        'https://i.pravatar.cc/56?img=34',
        4.8,
        'MAÑANA: 09:30 AM'
    ),
    (
        (
            SELECT id_usuario
            FROM Usuario
            WHERE
                correo = 'maria.paez@medisys.com'
        ),
        (
            SELECT id_especialidad
            FROM Especialidad
            WHERE
                nombre = 'Neurología'
        ),
        '66421',
        '999888777',
        14,
        'https://i.pravatar.cc/56?img=38',
        4.9,
        'DISPONIBLE HOY'
    ),
    (
        (
            SELECT id_usuario
            FROM Usuario
            WHERE
                correo = 'luis.cruz@medisys.com'
        ),
        (
            SELECT id_especialidad
            FROM Especialidad
            WHERE
                nombre = 'Neurología'
        ),
        '22711',
        '999888888',
        11,
        'https://i.pravatar.cc/56?img=52',
        4.7,
        'MAÑANA: 11:00 AM'
    );