SELECT
  generate_series AS id,
  json_build_object('foo',generate_series,'bar',2) AS obj
FROM 
  generate_series(1, 200)