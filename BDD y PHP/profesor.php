<?php
// Establecer variables de conexión a la base de datos
$host = "localhost";
$dbname = "id20713665_escuela";
$username = "id20713665_root";
$password = "lp1-UFC*Y%kYA/A+";

// Establecer credenciales para la autenticación básica
$auth_username = "admin";
$auth_password = "admin123";


// Conectar a la base de datos
try {
    $pdo = new PDO("mysql:host=$host;dbname=$dbname", $username, $password);
} catch (PDOException $e) {
    die("Error de conexión: " . $e->getMessage());
}

// Establecer el encabezado de respuesta a JSON
header('Content-Type: application/json');

// Comprobar el método HTTP utilizado
$method = $_SERVER['REQUEST_METHOD'];
switch ($method) {
    case 'GET':
        // Obtener un alumno específico o todos los alumnos
        if (isset($_GET['id'])) {
            // Obtener un alumno específico
            $stmt = $pdo->prepare("SELECT * FROM profesores WHERE id = ? ");
            $stmt->execute([$_GET['id']]);
            $alumno = $stmt->fetch(PDO::FETCH_ASSOC);
            echo json_encode($alumno);
        } else {
            // Obtener todos los alumnos
            $stmt = $pdo->query("SELECT * FROM profesores order by id desc");
            $alumnos = $stmt->fetchAll(PDO::FETCH_ASSOC);
            echo json_encode($alumnos);
        }
        break;
    case 'POST':
        if (isset($_GET['id'])) {
            // Actualizar o eliminar un alumno existente
            switch ($_GET['operacion']) {
                case 'actualizar':
                    $data = json_decode(file_get_contents('php://input'), true);
                    $stmt = $pdo->prepare("UPDATE profesores SET nombre = ?, apellido = ?, edad = ? WHERE id = ?");
                    $stmt->execute([$data['nombre'], $data['apellido'], $data['edad'], $_GET['id']]);
                    echo json_encode(['mensaje' => 'El profesor ha sido actualizado correctamente.']);
                    break;
                case 'eliminar':
                    $stmt = $pdo->prepare("DELETE FROM profesores WHERE id = ?");
                    $stmt->execute([$_GET['id']]);
                    echo json_encode(['mensaje' => 'El profesor ha sido eliminado correctamente.']);
                    break;
                default:
                    // Operación no válida
                    header('HTTP/1.1 400 Bad Request');
                    echo json_encode(['error' => 'Operación no válida.']);
                    break;
            }
         }else{ 
        // Crear un nuevo alumno
        $data = json_decode(file_get_contents('php://input'), true);
        $stmt = $pdo->prepare("INSERT INTO profesores (nombre, apellido, edad) VALUES (?, ?, ?)");
        $stmt->execute([$data['nombre'], $data['apellido'], $data['edad']]);
        $alumno_id = $pdo->lastInsertId();
        $alumno = [
            'id' => $alumno_id,
            'nombre' => $data['nombre'],
            'apellido' => $data['apellido'],
            'edad' => $data['edad']
        ];
        echo json_encode($data);
            }
        break;
    default:
        // Método HTTP no válido
        header('HTTP/1.1 405 Method Not Allowed-');
        echo json_encode(['error' => 'Método HTTP no válido-']);
        break;
  }

//Cerrar la conexión con la base de datos
$pdo = null;
?>
