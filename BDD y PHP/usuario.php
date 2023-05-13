<?php
// Establecer variables de conexión a la base de datos
$host = "localhost";
$dbname = "id20713665_escuela";
$username = "id20713665_root";
$password = "lp1-UFC*Y%kYA/A+";


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
    case 'POST':
       // Obtener un alumno específico
            $data = json_decode(file_get_contents('php://input'), true);
            $stmt = $pdo->prepare("SELECT * FROM usuarios WHERE username = ? AND password = ?");
            $stmt->execute([$data['username'],$data['password'],]);
            $alumno = $stmt->fetch(PDO::FETCH_ASSOC);
            if(!$alumno){
                // Devolvemos una respuesta de error si los datos de usuario son incorrectos
                header('HTTP/1.1 401 Unauthorized');
                echo json_encode(['error' => 'Credenciales de usuario incorrectas']);
                exit();
            }else{
                echo json_encode(['mensaje' => 'Bienvenido']);
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