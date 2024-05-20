#  Optimización de bases de datos

## Estrategias avanzadas de indexación

### Guías para Índices Específicos de la Aplicación

Se pueden crear índices en columnas para acelerar las consultas. Los índices proporcionan un acceso más rápido a los datos para operaciones que devuelven una pequeña porción de las filas de una tabla.

En general, se deberia de crear un índice en una columna en cualquiera de las siguientes situaciones:

- La columna se consulta con frecuencia.
- Existe una restricción de integridad referencial en la columna.
- Existe una restricción de integridad de clave ÚNICA en la columna.

Se puede crear un índice en cualquier columna; sin embargo, si la columna no se utiliza en ninguna de estas situaciones, crear un índice en la columna no aumentará el rendimiento y el índice ocupará recursos innecesariamente.

Aunque la base de datos crea un índice por ti en una columna con una restricción de integridad, se recomienda crear explícitamente un índice en dicha columna.

Puedes utilizar las siguientes técnicas para determinar qué columnas son los mejores candidatos para indexar:

- Utiliza la función EXPLAIN PLAN para mostrar un plan de ejecución teórico de una consulta dada.

### Desventajas de utilizar índices en bases de datos SQL:

- **Espacio adicional:** Los índices ocupan espacio adicional en el disco, lo que puede ser significativo en bases de datos con muchas tablas grandes y varios índices creados.
- **Costo de mantenimiento:** A medida que los datos se insertan, actualizan o eliminan de la tabla, los índices también deben actualizarse, lo que puede implicar cierto costo en términos de rendimiento en las operaciones de escritura.
- **Posible degradación del rendimiento:** Un mal diseño de índices o la creación de índices innecesarios puede conducir a un rendimiento más lento en lugar de mejorarlo.

### Tipos de Indices
#### 1. Índices Compuestos

Un índice compuesto es un índice en múltiples columnas de una tabla. Es útil cuando las consultas filtran y ordenan por varias columnas.

**Ejemplo**:

```sql
CREATE INDEX idx_employee_name ON employees (last_name, first_name);
```

**Uso**:

```sql
SELECT * FROM employees WHERE last_name = 'Smith' AND first_name = 'John';
```

Los índices compuestos pueden ser muy eficientes si la columna en el índice principal tiene una alta selectividad.

#### 2. Índices Únicos

Los índices únicos garantizan que todos los valores en la columna indexada sean distintos. Son útiles para columnas que deben ser únicas, como correos electrónicos o números de identificación.

**Ejemplo**:

```sql
CREATE UNIQUE INDEX idx_email_unique ON users (email);
```

#### 3. Índices de Cobertura

Un índice de cobertura incluye todas las columnas necesarias para una consulta, permitiendo que la base de datos responda la consulta usando solo el índice, sin acceder a la tabla base.

**Ejemplo**:

```sql
CREATE INDEX idx_order_covering ON orders (order_id, order_date, customer_id);
```

**Uso**:

```sql
SELECT order_id, order_date, customer_id FROM orders WHERE order_date = '2023-01-01';
```

#### 4. Índices Parciales

Los índices parciales (o índices filtrados) son índices que cubren solo una parte de las filas de la tabla, basados en una condición.

**Ejemplo**:

```sql
CREATE INDEX idx_active_users ON users (last_login) WHERE status = 'active';
```

**Uso**:

```sql
SELECT * FROM users WHERE status = 'active' AND last_login > '2024-01-01';
```

#### 5. Índices de Funciones

Algunas bases de datos permiten crear índices en el resultado de una función, útil para consultas que utilizan funciones sobre columnas.

**Ejemplo en PostgreSQL**:

```sql
CREATE INDEX idx_lower_email ON users (LOWER(email));
```

**Uso**:

```sql
SELECT * FROM users WHERE LOWER(email) = 'user@example.com';
```

#### 6. Índices de Texto Completo

Para búsquedas de texto en columnas grandes o documentos, los índices de texto completo permiten búsquedas eficientes.

**Ejemplo en PostgreSQL**:

```sql
CREATE INDEX idx_fulltext ON documents USING gin(to_tsvector('english', content));
```

**Uso**:

```sql
SELECT * FROM documents WHERE to_tsvector('english', content) @@ to_tsquery('search_query');
```

#### 7. Índices Clustered

En algunas bases de datos como SQL Server, un índice clustered determina el orden físico de los datos en la tabla. Solo puede haber un índice clustered por tabla.

**Ejemplo en SQL Server**:

```sql
CREATE CLUSTERED INDEX idx_clustered ON orders (order_id);
```

#### 8. Índices Hash

Los índices hash son ideales para búsquedas exactas y rápidas, pero no son adecuados para rangos de búsqueda.

**Ejemplo en PostgreSQL**:

```sql
CREATE INDEX idx_hash ON users USING hash (user_id);
```

#### 9. Índices Multicolumna (Multi-Column Indexes)

Estos índices son útiles cuando las consultas involucran combinaciones de columnas en las cláusulas WHERE.

**Ejemplo**:

```sql
CREATE INDEX idx_multicolumn ON sales (region, product, sales_date);
```

**Uso**:

```sql
SELECT * FROM sales WHERE region = 'North' AND product = 'Laptop' AND sales_date = '2024-01-01';
```

#### 10. Estrategias de Mantenimiento de Índices

Para mantener los índices eficientes, es crucial realizar tareas de mantenimiento regular como la reconstrucción y reindexación.

**Ejemplo en PostgreSQL**:

```sql
REINDEX TABLE employees;
```

**Ejemplo en SQL Server**:

```sql
ALTER INDEX ALL ON employees REBUILD;
```

###  Demostración de Estrategias Avanzadas

#### Caso Práctico: Optimización de Consultas con Índices Compuestos y de Cobertura

**Contexto**: Una tienda en línea necesita optimizar las consultas para obtener detalles de pedidos por fecha y estado.

**Configuración del Índice**:

```sql
CREATE INDEX idx_orders_date_status ON orders (order_date, status, customer_id);
```

**Consulta Optimizada**:

```sql
EXPLAIN
SELECT order_id, customer_id FROM orders WHERE order_date = '2024-01-01' AND status = 'shipped';
```

**Salida de EXPLAIN**:

```plaintext
Index Scan using idx_orders_date_status on orders (cost=0.42..4.57 rows=1 width=16)
  Index Cond: ((order_date = '2024-01-01'::date) AND (status = 'shipped'::text))
```

#### Mantenimiento Regular de Índices

Para asegurar que los índices permanezcan eficientes, se debe realizar mantenimiento regular, especialmente en tablas con alta actividad de inserción, actualización o eliminación.

**Ejemplo en PostgreSQL**:

```sql
REINDEX INDEX idx_orders_date_status;
```

## Ejemplo
### Crear Tabla y Datos de Ejemplo

```sql
CREATE TABLE users (
    user_id INT PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    status VARCHAR(20),
    last_login TIMESTAMP
);

INSERT INTO users (user_id, first_name, last_name, status, last_login) VALUES
(1, 'John', 'Doe', 'active', '2024-05-17 10:00:00'),
(2, 'Jane', 'Smith', 'inactive', '2024-05-16 09:30:00'),
(3, 'Alice', 'Johnson', 'suspended', '2024-05-15 14:45:00'),
-- Agregar más datos según sea necesario
;
```

### Consulta Antes de Crear el Índice

Queremos consultar los usuarios activos por nombre y apellido. Ejecutamos la consulta sin ningún índice:

```sql
EXPLAIN SELECT * FROM users WHERE status = 'active' AND last_name = 'Doe' AND first_name = 'John';
```

**Salida de EXPLAIN (Antes de Crear el Índice)**:

```
+----+-------------+-------+------+---------------+------+---------+------+---------+-------------+
| id | select_type | table | type | possible_keys | key  | key_len | ref  | rows    | Extra       |
+----+-------------+-------+------+---------------+------+---------+------+---------+-------------+
|  1 | SIMPLE      | users | ALL  | NULL          | NULL | NULL    | NULL | 1000000 | Using where |
+----+-------------+-------+------+---------------+------+---------+------+---------+-------------+
```

- **type = ALL**: Indica que se realiza un escaneo completo de la tabla.
- **rows**: Número estimado de filas que la base de datos examinará.

### Crear Índice

Ahora, creamos un índice para optimizar esta consulta. El índice puede ser un índice compuesto en las columnas `status`, `last_name` y `first_name`.

```sql
CREATE INDEX idx_active_users_name ON users (status, last_name, first_name);
```

### Consulta Después de Crear el Índice

Ejecutamos la misma consulta después de crear el índice:

```sql
EXPLAIN SELECT * FROM users WHERE status = 'active' AND last_name = 'Doe' AND first_name = 'John';
```

**Salida de EXPLAIN (Después de Crear el Índice)**:

```
+----+-------------+-------+------+---------------------+---------------------+---------+-------------+------+-----------------+
| id | select_type | table | type | possible_keys       | key                 | key_len | ref         | rows | Extra           |
+----+-------------+-------+------+---------------------+---------------------+---------+-------------+------+-----------------+
|  1 | SIMPLE      | users | ref  | idx_active_users_name | idx_active_users_name | 152     | const,const | 1    | Using index condition |
+----+-------------+-------+------+---------------------+---------------------+---------+-------------+------+-----------------+
```

- **type = ref**: Indica que se utiliza un índice para buscar las filas.
- **key = idx_active_users_name**: Muestra que el índice `idx_active_users_name` se está utilizando.
- **rows**: Número estimado de filas que la base de datos examinará (mucho menor comparado con el escaneo completo).

### Comparación Antes y Después de Crear el Índice

**Antes de Crear el Índice:**
- **Escaneo Completo de Tabla (type = ALL)**: La base de datos examina todas las filas de la tabla, lo cual es ineficiente para tablas grandes.
- **Lento**: La consulta puede ser muy lenta si la tabla contiene muchos registros.

**Después de Crear el Índice:**
- **Búsqueda por Índice (type = ref)**: La base de datos utiliza el índice para localizar rápidamente las filas pertinentes.
- **Rápido**: La consulta es mucho más rápida ya que solo un subconjunto de las filas necesita ser examinado.

### Conclusión

Crear índices adecuados puede transformar significativamente el rendimiento de las consultas en una base de datos relacional. Utilizar `EXPLAIN` para analizar las consultas antes y después de la creación del índice te permite comprender cómo el motor de la base de datos está ejecutando la consulta y cómo los índices mejoran el rendimiento. Esta estrategia es aplicable a la mayoría de los sistemas de gestión de bases de datos relacionales, como PostgreSQL, MySQL, Oracle, SQL Server, entre otros.

### Recursos Adicionales

1. **[PostgreSQL Documentation on Indexes](https://www.postgresql.org/docs/current/indexes.html)**
2. **[MySQL Documentation on Indexes](https://dev.mysql.com/doc/refman/8.0/en/mysql-indexes.html)**
3. **[SQL Server Index Design Guide](https://docs.microsoft.com/en-us/sql/relational-databases/sql-server-index-design-guide?view=sql-server-ver15)**
4. **[Oracle Indexing Techniques](https://docs.oracle.com/en/database/oracle/oracle-database/19/tgsql/indexing-techniques.html)**

### Índices en Bases de Datos NoSQL

Las bases de datos NoSQL tienen distintas técnicas de indexación debido a sus diferentes modelos de datos y objetivos de diseño. A continuación se presentan algunas estrategias comunes de indexación para varias bases de datos NoSQL populares:

#### MongoDB

- **Índices Compuestos**: Índices en múltiples campos.
  ```javascript
  db.users.createIndex({ lastName: 1, firstName: 1 });
  ```
- **Índices Parciales**: Índices en un subconjunto de documentos.
  ```javascript
  db.orders.createIndex({ status: 1 }, { partialFilterExpression: { status: { $eq: "shipped" } } });
  ```
- **Índices de Texto Completo**: Para búsquedas de texto.
  ```javascript
  db.articles.createIndex({ content: "text" });
  ```
- **Índices Geoespaciales**: Para datos de ubicación.
  ```javascript
  db.places.createIndex({ location: "2dsphere" });
  ```
- **Índices TTL**: Para la eliminación automática de documentos después de un tiempo.
  ```javascript
  db.sessions.createIndex({ "createdAt": 1 }, { expireAfterSeconds: 3600 });
  ```

#### Cassandra

- **Índices Secundarios**: Índices en columnas no clave.
  ```sql
  CREATE INDEX ON users (last_name);
  ```
- **Materialized Views**: Vistas predefinidas para consultas rápidas.
  ```sql
  CREATE MATERIALIZED VIEW users_by_email AS
    SELECT * FROM users
    WHERE email IS NOT NULL
    PRIMARY KEY (email, user_id);
  ```
- **Clustering Columns**: Ordenación de datos en el nivel de partición.
  ```sql
  CREATE TABLE orders (
    user_id UUID,
    order_id UUID,
    order_date TIMESTAMP,
    PRIMARY KEY (user_id, order_date)
  ) WITH CLUSTERING ORDER BY (order_date DESC);
  ```

#### Redis

- **Sorted Sets**: Para crear índices secundarios.
  ```redis
  ZADD user_last_login 1617670400 user:1
  ZADD user_last_login 1617756800 user:2

  ZRANGEBYSCORE user_last_login 1617670400 1617756800
  ```
- **Bloom Filters**: Para verificar la existencia de elementos eficientemente.
  ```redis
  BF.ADD mybloom user@example.com
  BF.EXISTS mybloom user@example.com
  ```

### Ejemplo Agnóstico para NoSQL

#### Crear Colección y Datos de Ejemplo

```javascript
db.users.insertMany([
  { user_id: 1, first_name: 'John', last_name: 'Doe', status: 'active', last_login: new Date("2024-05-17T10:00:00Z") },
  { user_id: 2, first_name: 'Jane', last_name: 'Smith', status: 'inactive', last_login: new Date("2024-05-16T09:30:00Z") },
  { user_id: 3, first_name: 'Alice', last_name: 'Johnson', status: 'suspended', last_login: new Date("2024-05-15T14:45:00Z") },
  // Agregar más datos según sea necesario
]);
```

#### Consulta Antes de Crear el Índice

Queremos consultar los usuarios activos por nombre y apellido. Ejecutamos la consulta sin ningún índice:

```javascript
db.users.find({ status: 'active', last_name: 'Doe', first_name: 'John' }).explain("executionStats");
```

**Salida de `explain` (Antes de Crear el Índice)**:

```json
{
  "queryPlanner": {
    "plannerVersion": 1,
    "namespace": "yourDatabase.users",
    "indexFilterSet": false,
    "winningPlan": {
      "stage": "COLLSCAN",
      "direction": "forward",
      "filter": {
        "status": { "$eq": "active" },
        "last_name": { "$eq": "Doe" },
        "first_name": { "$eq": "John" }
      }
    },
    "rejectedPlans": []
  },
  "executionStats": {
    "executionSuccess": true,
    "nReturned": 1,
    "executionTimeMillis": 23,
    "totalKeysExamined": 0,
    "totalDocsExamined": 1000000,
    "executionStages": {
      "stage": "COLLSCAN",
      "filter": {
        "status": { "$eq": "active" },
        "last_name": { "$eq": "Doe" },
        "first_name": { "$eq": "John" }
      },
      "nReturned": 1,
      "executionTimeMillisEstimate": 10,
      "works": 1000002,
      "advanced": 1,
      "needTime": 1000000,
      "needYield": 0,
      "saveState": 7814,
      "restoreState": 7814,
      "isEOF": 1,
      "direction": "forward",
      "docsExamined": 1000000
    }
  }
}
```

- **stage = COLLSCAN**: Indica que se realiza un escaneo completo de la colección.
- **docsExamined**: Número de documentos examinados (alto).

#### Crear Índice

Ahora, creamos un índice para optimizar esta consulta. El índice puede ser un índice compuesto en las columnas `status`, `last_name` y `first_name`.

```javascript
db.users.createIndex({ status: 1, last_name: 1, first_name: 1 });
```

#### Consulta Después de Crear el Índice

Ejecutamos la misma consulta después de crear el índice:

```javascript
db.users.find({ status: 'active', last_name: 'Doe', first_name: 'John' }).explain("executionStats");
```

**Salida de `explain` (Después de Crear el Índice)**:

```json
{
  "queryPlanner": {
    "plannerVersion": 1,
    "namespace": "yourDatabase.users",
    "indexFilterSet": false,
    "winningPlan": {
      "stage": "FETCH",
      "inputStage": {
        "stage": "IXSCAN",
        "keyPattern": { "status": 1, "last_name": 1, "first_name": 1 },
        "indexName": "status_1_last_name_1_first_name_1",
        "direction": "forward",
        "indexBounds": {
          "status": ["[\"active\", \"active\"]"],
          "last_name": ["[\"Doe\", \"Doe\"]"],
          "first_name": ["[\"John\", \"John\"]"]
        }
      }
    },
    "rejectedPlans": []
  },
  "executionStats": {
    "executionSuccess": true,
    "nReturned": 1,
    "executionTimeMillis": 3,
    "totalKeysExamined": 1,
    "totalDocsExamined": 1,
    "executionStages": {
      "stage": "FETCH",
      "nReturned": 1,
      "executionTimeMillisEstimate": 2,
      "works": 2,
      "advanced": 1,
      "needTime": 0,
      "needYield": 0,
      "saveState": 0,
      "restoreState": 0,
      "isEOF": 1,
      "docsExamined": 1,
      "inputStage": {
        "stage": "IXSCAN",
        "nReturned": 1,
        "executionTimeMillisEstimate": 1,
        "works": 2,
        "advanced": 1,
        "needTime": 0,
        "needYield": 0,
        "saveState": 0,
        "restoreState": 0,
        "isEOF": 1,
        "keyPattern": { "status": 1, "last_name": 1, "first_name": 1 },
        "indexName": "status_1_last_name_1_first_name_1",
        "direction": "forward",
        "indexBounds": {
          "status": ["[\"active\", \"active\"]"],
          "last_name": ["[\"Doe\", \"Doe\"]"],
          "first_name": ["[\"John\", \"John\"]"]
        },
        "keysExamined": 1
      }
    }
  }
}
```

- **stage = IXSCAN**: Indica que se utiliza un índice para buscar las filas.
- **keysExamined**: Número de claves examinadas (bajo).
- **docsExamined**: Número de documentos examinados (mucho menor).

### Comparación Antes y Después de Crear el Índice

**Antes de Crear el Índice:**
- **Escaneo Completo de Colección (stage = COLLSCAN)**: La base de datos examina todos los documentos de la colección, lo cual es ineficiente para colecciones grandes.
- **Lento**: La consulta puede ser muy lenta si la colección contiene muchos documentos.

**Después de Crear el Índice:**
- **Búsqueda por Índice (stage = IXSCAN)**: La base de datos utiliza el índice para localizar rápidamente los documentos pertinentes.
- **Rápido**: La consulta es mucho más rápida ya que solo un subconjunto de los documentos necesita ser examinado.

### Conclusión

Crear índices adecuados en bases de datos NoSQL, como en MongoDB,