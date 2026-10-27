import 'package:proyecto_dart/src/cli/menu_cli.dart';
import 'package:proyecto_dart/src/services/gestor_sistema_service.dart';

void main() {
  MenuCli(GestorSistemaService()).iniciar();
}
