-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 23-05-2025 a las 23:50:49
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `dbsistemasventa`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `articulos`
--

CREATE TABLE `articulos` (
  `id` int(11) NOT NULL,
  `id_categoria` int(11) NOT NULL,
  `id_proveedor` int(11) NOT NULL,
  `codigo` varchar(50) DEFAULT NULL,
  `nombre` varchar(100) NOT NULL,
  `precio_venta` decimal(11,2) NOT NULL,
  `stock` int(11) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `imagen` varchar(255) DEFAULT NULL,
  `estado` bit(1) DEFAULT b'1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `articulos`
--

INSERT INTO `articulos` (`id`, `id_categoria`, `id_proveedor`, `codigo`, `nombre`, `precio_venta`, `stock`, `descripcion`, `imagen`, `estado`) VALUES
(1, 7, 1, 'A0001', 'martillo mediano', 100.00, 26, 'metalico', 'src/imgs/1747528506033_51yMSvDN4aL._AC_SL1283_.jpg', b'1'),
(2, 3, 2, 'A0002', 'Clavo Fiskars', 25.00, 78, 'Clavos metalicos 2 pulgadas', 'src/imgs/1747528404098_Clavo-de-acero-guarda-escobas-2.jpg', b'1'),
(3, 5, 1, 'A0003', 'Rodillo', 30.00, 30, 'Rodillo', 'src/imgs/1747528789616_1747528455841_images.jpg', b'1'),
(4, 1, 1, 'A00005', 'Tornillo', 25.00, 69, 'Bolsas de 100', 'src/imgs/1733072550431_Buho.png', b'1'),
(5, 1, 2, 'A00009', 'Tornillos 3plg', 10.00, 40, 'Torinillo metalico 3plg / 50 unidades', 'src/imgs/1733073838487_images.jpg', b'1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categorias`
--

CREATE TABLE `categorias` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `estado` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `categorias`
--

INSERT INTO `categorias` (`id`, `nombre`, `descripcion`, `estado`) VALUES
(1, 'Tornillos', 'Todo lo relacionado a tornillos', b'1'),
(2, 'Ladrillos y bloques', NULL, b'1'),
(3, 'Clavos', NULL, b'1'),
(4, 'Pinturas', NULL, b'0'),
(5, 'Rodillos y Brochas', NULL, b'1'),
(6, 'Prueba', 'Prueba', b'1'),
(7, 'Martillos', 'Todas las marcas de martillos', b'1'),
(8, 'Taladros', 'Todas las marcas de taladros', NULL),
(9, 'Barnices', 'Todas las marcas de barnices', b'1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `id` int(11) NOT NULL,
  `tipo_cliente` varchar(20) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `tipo_documento` varchar(20) DEFAULT NULL,
  `num_documento` varchar(20) DEFAULT NULL,
  `direccion` varchar(70) DEFAULT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `estado` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`id`, `tipo_cliente`, `nombre`, `tipo_documento`, `num_documento`, `direccion`, `telefono`, `email`, `estado`) VALUES
(1, '', 'Martin Perez', 'NIT', '798555455', 'Villa Fatima', '2225696', 'MartinPerez@gmail.com', b'1'),
(2, 'regular', 'Miguel Gomez', 'CI', '77777777', 'Calacoto', '77777777', 'Miguel@gmail.com', b'1'),
(3, 'regular', 'Alan Jimenez', 'CI', '1335669', 'Villa copacabana', '75266699', 'Alan@gmail.com', b'1'),
(4, 'regular', 'Pedro Mejia', 'CI', '7899548', 'El alto', '7589666', 'Pedro@gmail.com', b'1'),
(5, 'regular', 'Daniel Rivas', 'Nit', '79888888555', '...', '78548965', 'S/N', b'1'),
(6, 'regular', 'Luis Conde', 'NIT', '88885669985', 'villa fatima', '7589698', 'luis@gmail.com', b'1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_venta`
--

CREATE TABLE `detalle_venta` (
  `id` int(11) NOT NULL,
  `id_venta` int(11) NOT NULL,
  `id_articulo` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `precio` decimal(11,2) NOT NULL,
  `descuento` decimal(11,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `detalle_venta`
--

INSERT INTO `detalle_venta` (`id`, `id_venta`, `id_articulo`, `cantidad`, `precio`, `descuento`) VALUES
(1, 8, 2, 10, 25.00, 250.00),
(2, 9, 2, 5, 25.00, 125.00),
(3, 10, 2, 5, 25.00, 125.00),
(4, 11, 2, 40, 25.00, 1000.00),
(5, 11, 2, 40, 25.00, 1000.00),
(6, 12, 4, 5, 25.00, 125.00),
(7, 12, 5, 1, 10.00, 10.00),
(8, 13, 1, 5, 100.00, 500.00),
(9, 13, 5, 4, 10.00, 40.00),
(10, 14, 1, 5, 100.00, 500.00),
(11, 14, 3, 5, 30.00, 150.00),
(12, 15, 1, 5, 100.00, 500.00),
(13, 15, 3, 5, 30.00, 150.00),
(14, 16, 1, 100, 100.00, 10000.00),
(15, 17, 1, 5, 100.00, 500.00),
(16, 18, 1, 5, 100.00, 500.00),
(17, 18, 5, 5, 10.00, 50.00),
(18, 19, 3, 5, 30.00, 150.00),
(19, 19, 1, 5, 100.00, 500.00),
(20, 20, 3, 5, 30.00, 150.00),
(21, 20, 4, 5, 25.00, 125.00),
(22, 21, 3, 25, 30.00, 750.00),
(23, 21, 3, 25, 30.00, 750.00),
(24, 22, 4, 5, 25.00, 125.00),
(25, 22, 1, 5, 100.00, 500.00),
(26, 23, 2, 5, 25.00, 125.00),
(27, 24, 4, 1, 25.00, 25.00),
(28, 25, 1, 1, 100.00, 100.00),
(29, 26, 4, 2, 25.00, 50.00),
(30, 27, 2, 5, 25.00, 125.00),
(31, 27, 4, 7, 25.00, 175.00),
(32, 28, 2, 5, 25.00, 125.00),
(33, 29, 2, 5, 25.00, 125.00),
(34, 29, 1, 1, 100.00, 100.00),
(35, 30, 4, 1, 25.00, 25.00),
(36, 31, 1, 2, 100.00, 200.00),
(37, 32, 1, 1, 100.00, 100.00),
(38, 32, 2, 2, 25.00, 50.00);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proveedores`
--

CREATE TABLE `proveedores` (
  `id` int(11) NOT NULL,
  `razon_social` varchar(100) NOT NULL,
  `direccion` varchar(70) DEFAULT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `estado` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `proveedores`
--

INSERT INTO `proveedores` (`id`, `razon_social`, `direccion`, `telefono`, `email`, `estado`) VALUES
(1, 'Bosch', 'El Alto', '78944455', 'Bosh@gmail.com', b'1'),
(2, 'Fiskars', NULL, NULL, NULL, b'1'),
(3, 'Milwaukee', '7899666', 'Villa Armonia', 'aa@hola.com', b'1'),
(4, 'Prueba', '72589666', 'Miraflores', 'Prueba@gmail.com', b'1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `roles`
--

CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `roles`
--

INSERT INTO `roles` (`id`, `nombre`, `descripcion`) VALUES
(1, 'Administrador', 'Todos los permisos'),
(2, 'Vendedor', 'Acceso solo a modulo de ventas'),
(3, 'Gerencia', 'Acceso a documentos'),
(5, 'Prueba', 'Preuba');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL,
  `id_rol` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `tipo_documento` varchar(20) DEFAULT NULL,
  `num_documento` varchar(20) DEFAULT NULL,
  `direccion` varchar(70) DEFAULT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  `clave` varchar(50) NOT NULL,
  `estado` bit(1) DEFAULT b'1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `id_rol`, `nombre`, `tipo_documento`, `num_documento`, `direccion`, `telefono`, `email`, `clave`, `estado`) VALUES
(1, 1, 'Juan Perez', 'CI', '1998875', 'Miraflores', '7896587', 'Juan@gmail.com', '123456', b'1'),
(2, 1, 'Maria Perez', 'CI', '78966555', 'Obrajes', '78546987', 'Maria@gmail.com', '123', b'1'),
(3, 2, 'Diego Mejia', 'CI', '7777777777', 'Cota Cota', '75896632', 'Diego@gmail.com', '123', b'1'),
(4, 1, 'Lucas Peña', 'CI', '785644449', 'Villa fatima', '77777777', 'Lucas@gmail.com', '456', b'1'),
(5, 1, 'Alan', 'CI', '7897897987', 'villa fatima', '77777777', 'Alan', '123', b'1'),
(6, 1, 'Marco Ruiz', 'CI', '7978888', 'no aplica', '72222565', 'marco@gmail.com', '456', b'0'),
(7, 1, 'Marco Ruiz', 'CI', '78555698', 'villa pabon', '78555669', 'marco@gmail.com', '456', b'0'),
(8, 1, 'Marco Ruiz', 'NIT', '72255589', 'Villa el carmen', '72555577', 'Marco@gmail.com', '123', b'1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ventas`
--

CREATE TABLE `ventas` (
  `id` int(11) NOT NULL,
  `id_cliente` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `tipo_comprobante` varchar(20) NOT NULL,
  `num_comprobante` varchar(10) NOT NULL,
  `fecha` datetime NOT NULL,
  `total` decimal(11,2) NOT NULL,
  `estado` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `ventas`
--

INSERT INTO `ventas` (`id`, `id_cliente`, `id_usuario`, `tipo_comprobante`, `num_comprobante`, `fecha`, `total`, `estado`) VALUES
(1, 1, 1, 'QR', '1', '0000-00-00 00:00:00', 200.00, '0'),
(2, 1, 1, 'Efectivo', '1', '2024-12-02 23:34:36', 500.00, '0'),
(3, 1, 1, 'Efectivo', '2', '2024-12-02 23:53:42', 125.00, '0'),
(4, 1, 1, 'Efectivo', '3', '2024-12-02 23:56:43', 125.00, '0'),
(5, 1, 1, 'Efectivo', '4', '2024-12-02 23:58:42', 125.00, '0'),
(6, 1, 1, 'Efectivo', '5', '2024-12-03 00:03:08', 500.00, '0'),
(7, 1, 1, 'Efectivo', '6', '2024-12-03 00:05:03', 125.00, '0'),
(8, 1, 1, 'Pago QR', '7', '2024-12-03 00:06:43', 250.00, '1'),
(9, 1, 1, 'Efectivo', '8', '2024-12-03 00:33:59', 125.00, '1'),
(10, 1, 1, 'Efectivo', '9', '2024-12-03 00:34:14', 125.00, '1'),
(11, 1, 1, 'Efectivo', '10', '2024-12-03 00:37:27', 2000.00, '1'),
(12, 2, 1, 'Tarjeta', '11', '2024-12-03 10:37:05', 135.00, '1'),
(13, 1, 1, 'Efectivo', '12', '2024-12-03 10:39:26', 540.00, '1'),
(14, 2, 1, 'Efectivo', '13', '2024-12-03 10:40:34', 650.00, '1'),
(15, 1, 1, 'Tarjeta', '14', '2024-12-03 11:35:02', 650.00, '1'),
(16, 1, 1, 'Efectivo', '15', '2024-12-03 11:37:08', 10000.00, '1'),
(17, 1, 1, 'Efectivo', '16', '2024-12-03 11:38:16', 500.00, '1'),
(18, 3, 1, 'Pago QR', '17', '2024-12-03 16:25:56', 550.00, '1'),
(19, 4, 1, 'Efectivo', '18', '2024-12-03 16:33:39', 650.00, '1'),
(20, 3, 5, 'Tarjeta', '19', '2025-02-25 23:07:59', 275.00, '1'),
(21, 3, 5, 'Efectivo', '20', '2025-05-05 15:38:44', 1500.00, '1'),
(22, 4, 5, 'Pago QR', '21', '2025-05-05 16:52:15', 625.00, '1'),
(23, 1, 5, 'Efectivo', '22', '2025-05-05 16:55:02', 125.00, '1'),
(24, 4, 5, 'Efectivo', '23', '2025-05-05 16:57:01', 25.00, '1'),
(25, 1, 5, 'Efectivo', '24', '2025-05-05 16:57:38', 100.00, '1'),
(26, 5, 5, 'Efectivo', '25', '2025-05-06 16:33:39', 50.00, '1'),
(27, 2, 5, 'Efectivo', '26', '2025-05-10 22:47:58', 300.00, '1'),
(28, 1, 5, 'Tarjeta', '27', '2025-05-11 16:19:56', 125.00, '1'),
(29, 6, 6, 'Pago QR', '28', '2025-05-12 23:04:40', 225.00, '1'),
(30, 1, 6, 'Efectivo', '29', '2025-05-12 23:06:16', 25.00, '1'),
(31, 2, 8, 'Efectivo', '30', '2025-05-16 19:50:22', 200.00, '1'),
(32, 1, 5, 'Pago QR', '31', '2025-05-17 20:35:58', 150.00, '1');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `articulos`
--
ALTER TABLE `articulos`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nombre` (`nombre`),
  ADD KEY `id_categoria` (`id_categoria`),
  ADD KEY `id_proveedor` (`id_proveedor`);

--
-- Indices de la tabla `categorias`
--
ALTER TABLE `categorias`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nombre` (`nombre`);

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `detalle_venta`
--
ALTER TABLE `detalle_venta`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_venta` (`id_venta`),
  ADD KEY `id_articulo` (`id_articulo`);

--
-- Indices de la tabla `proveedores`
--
ALTER TABLE `proveedores`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_rol` (`id_rol`);

--
-- Indices de la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_cliente` (`id_cliente`),
  ADD KEY `id_usuario` (`id_usuario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `articulos`
--
ALTER TABLE `articulos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `categorias`
--
ALTER TABLE `categorias`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `clientes`
--
ALTER TABLE `clientes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `detalle_venta`
--
ALTER TABLE `detalle_venta`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

--
-- AUTO_INCREMENT de la tabla `proveedores`
--
ALTER TABLE `proveedores`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `ventas`
--
ALTER TABLE `ventas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `articulos`
--
ALTER TABLE `articulos`
  ADD CONSTRAINT `articulos_ibfk_1` FOREIGN KEY (`id_categoria`) REFERENCES `categorias` (`id`),
  ADD CONSTRAINT `articulos_ibfk_2` FOREIGN KEY (`id_proveedor`) REFERENCES `proveedores` (`id`);

--
-- Filtros para la tabla `detalle_venta`
--
ALTER TABLE `detalle_venta`
  ADD CONSTRAINT `detalle_venta_ibfk_1` FOREIGN KEY (`id_venta`) REFERENCES `ventas` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `detalle_venta_ibfk_2` FOREIGN KEY (`id_articulo`) REFERENCES `articulos` (`id`);

--
-- Filtros para la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD CONSTRAINT `usuarios_ibfk_1` FOREIGN KEY (`id_rol`) REFERENCES `roles` (`id`);

--
-- Filtros para la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD CONSTRAINT `ventas_ibfk_1` FOREIGN KEY (`id_cliente`) REFERENCES `clientes` (`id`),
  ADD CONSTRAINT `ventas_ibfk_2` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
