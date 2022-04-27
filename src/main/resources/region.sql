WITH 

tablemock AS (
  SELECT
    generate_series::text AS ident,
    json_build_object('foo',1,'bar',2) AS obj
  FROM 
    generate_series(1, 200)
)

SELECT * FROM tablemock WHERE ident = ?