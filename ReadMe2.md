# Aplicació de Gestió d'Encàrrecs

## Descripció
Aquesta aplicació permet gestionar encàrrecs de clients. L'usuari pot generar nous encàrrecs, mostrar encàrrecs ja existents i editar informació dels encàrrecs en diferents formats de fitxer (TXT, CSV, Binari, Accés Aleatori). L'aplicació gestiona la creació i modificació de fitxers així com la lectura dels mateixos.

## Requisits
- És necessari tenir una carpeta amb permisos de lectura i escriptura en aquesta ruta: `C:\Users\accesadades`
- Java Runtime per a executar l'aplicació

## Funcionalitats

### 1. Generar un nou encàrrec
Quan l'usuari selecciona aquesta opció, se li demanen les dades del client (nom, telèfon i data de l'encàrrec) i els articles que vol afegir. Per cada article, l'usuari ha de proporcionar:
- Nom de l'article
- Quantitat
- Unitat (kg, unitats, etc.)

L'usuari pot afegir tants articles com vulgui i, després, pot triar el format de fitxer per a guardar l'encàrrec:
- **Albarà (TXT)**: Genera un fitxer `.txt` amb estructura llegible.
- **CSV**: Genera un fitxer `.csv` en format delimitat per comes.
- **Binari**: Guarda les dades en format codificat en un fitxer binari.
- **Accés Aleatori**: Guarda les dades en un fitxer per accés aleatori que permet la lectura i modificació de dades concretes.

### 2. Mostrar un encàrrec
Permet obrir un encàrrec guardat en format CSV, Binari, Accés Aleatori o Serialitzat. Es mostrarà la informació del client i dels articles en format llegible a la consola.

### 3. Editar un encàrrec
Permet a l'usuari modificar el telèfon i la data d'un encàrrec existent en un fitxer d'accés aleatori. S'introdueix la ruta del fitxer, l'ID de l'encàrrec, el nou telèfon i la nova data per fer les modificacions directament en el fitxer sense alterar les altres dades.

### 4. Sortir
Finalitza l'aplicació.

## Documentació del Codi

### 1. Bucle Principal
El programa executa un bucle que mostra el menú d'opcions fins que l'usuari decideix sortir.

### 2. Generar un nou encàrrec
Quan l'usuari selecciona l'opció 'a' (generar encàrrec):
- Es demanen les dades bàsiques del client (nom, telèfon i data).
- Es recullen les dades dels articles que vol afegir a l'encàrrec.
- Finalment, es tria el tipus de fitxer en el qual es vol guardar l'encàrrec:
    - **TXT**: Fitxer amb estructura llegible.
    - **CSV**: Fitxer delimitat per comes.
    - **Binari**: Fitxer codificat.
    - **Accés Aleatori**: Fitxer que permet la modificació directa de camps específics.

### 3. Mostrar un encàrrec
Quan l'usuari selecciona l'opció 'b', el programa permet obrir un fitxer existent en format CSV, Binari, Accés Aleatori o Serialitzat, mostrant les dades del client i els articles en format llegible.

### 4. Editar un encàrrec
L'opció 'c' permet editar el telèfon i la data d'un encàrrec en un fitxer d'accés aleatori. L'usuari proporciona la ruta del fitxer, l'ID de l'encàrrec i les noves dades de telèfon i data. Si l'encàrrec es troba, les dades es modifiquen directament en el fitxer.

### Estructura del Codi
- **BufferedReader**: Per a la lectura d'entrada de l'usuari.
- **ArrayList**: Per emmagatzemar les dades dels articles.
- **DataOutputStream / DataInputStream**: Per escriure i llegir fitxers en format binari.
- **RandomAccessFile**: Per accedir a un encàrrec específic en un fitxer d'accés aleatori.
- **ObjectOutputStream / ObjectInputStream**: Per a la serialització i deserialització d'objectes.

## Classes

### Article
La classe `Article` representa un article de l'encàrrec, amb els següents atributs:
- `nom` (String): Nom de l'article.
- `quantitat` (double): Quantitat de l'article.
- `unitat` (String): Unitat de mesura (kg, unitats, etc.).
- `preu` (double): Preu unitari de l'article.

#### Mètodes de la classe `Article`
- `getNom()`, `getQuantitat()`, `getUnitat()`, `getPreu()`: Retornen els valors corresponents de cada atribut.
- `toCSV()`: Retorna una representació en format CSV de l'article.

### Encarrec
La classe `Encarrec` representa l'encàrrec complet i conté:
- `idEncarrec` (int): ID únic de l'encàrrec.
- `nomClient` (String): Nom del client.
- `telefon` (String): Telèfon del client.
- `dataEncarrec` (String): Data de l'encàrrec.
- `articles` (List<Article>): Llista d'articles de l'encàrrec.
- `preuTotal` (double): Preu total calculat dels articles.

#### Mètodes de la classe `Encarrec`
- `calculaPreuTotal()`: Calcula el preu total dels articles.
- `toString()`: Retorna una representació en format llegible de l'encàrrec.

## Com Utilitzar l'Aplicació

1. Executa el programa i segueix les instruccions a la consola.
2. Tria l'opció de generar, mostrar o editar un encàrrec.
3. Segueix les instruccions per introduir les dades del client i els articles.
4. Selecciona el tipus de fitxer que vols generar o introdueix la ruta per mostrar o modificar un encàrrec.
5. Per sortir, selecciona l'opció `d` del menú.

## Exemples d'ús

- **Generar un encàrrec**: Pots introduir les dades del client i afegir articles a l'encàrrec, escollint després el format de fitxer per guardar.
- **Editar un encàrrec d'accés aleatori**: Indica l'ID de l'encàrrec i les noves dades per modificar-lo directament en el fitxer.
