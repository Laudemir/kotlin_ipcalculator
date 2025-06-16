# Kotlin IP Calculator

Este é um aplicativo Android desenvolvido em **Kotlin** que realiza o **cálculo de redes IPv4**, permitindo que o usuário insira um IP e uma máscara de sub-rede e obtenha informações detalhadas sobre a rede.

## 📱 Funcionalidades

- Entrada de endereço IP (com validação)
- Seleção ou digitação da máscara de sub-rede
- Cálculo automático das propriedades da sub-rede:
  - Endereço de rede
  - Primeiro host
  - Último host
  - Endereço de broadcast
  - Quantidade de hosts disponíveis

## 💻 Tecnologias Utilizadas

- Kotlin
- Android SDK
- Android Studio
- ConstraintLayout
- Componentes nativos do Android
- (Opcional) ViewBinding ou DataBinding

## 🚀 Como Executar o Projeto

1. Clone este repositório:
   ```bash
   git clone https://github.com/Laudemir/kotlin_ipcalculator.git
   ```
2. Abra o projeto no **Android Studio**
3. Conecte um dispositivo Android físico ou emulador
4. Clique em **Run** ▶️ para compilar e executar

## 💡 Exemplo de Uso

Entrada:

```
IP: 192.168.0.10
Máscara: 255.255.255.0
```

Saída:

```
Endereço de Rede: 192.168.0.0
Primeiro Host: 192.168.0.1
Último Host: 192.168.0.254
Broadcast: 192.168.0.255
Hosts Disponíveis: 254
```

## 📸 Captura de Tela
![tela_ipcalculator](https://github.com/user-attachments/assets/f555fd6d-955d-4716-854a-40e826813506)

## 🙇‍♂️ Autor

Desenvolvido por [Laudemir Oliveira](https://github.com/Laudemir)\
Sinta-se à vontade para usar, modificar e contribuir com este projeto!

---
