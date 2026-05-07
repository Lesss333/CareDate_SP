    package org.example.com.caredate.model.controller;

    import org.example.com.caredate.model.enums.EstadoCita;
    import org.example.com.caredate.repository.CitaRepository;
    import org.example.com.caredate.util.ApiResponse;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;

    import java.util.LinkedHashMap;
    import java.util.Map;

    @RestController
    @RequestMapping("/estadisticas")
    public class EstadisticaController {

        private final CitaRepository citaRepository;

        public EstadisticaController(CitaRepository citaRepository) {
            this.citaRepository = citaRepository;
        }

        @GetMapping("/citas/por-estado")
        public ApiResponse<Map<String, Long>> citasPorEstado() {
            Map<String, Long> data = new LinkedHashMap<>();
            data.put("pendientes", citaRepository.countByEstado(EstadoCita.PENDIENTE));
            data.put("confirmadas", citaRepository.countByEstado(EstadoCita.CONFIRMADA));
            data.put("canceladas", citaRepository.countByEstado(EstadoCita.CANCELADA));
            data.put("asistio", citaRepository.countByEstado(EstadoCita.ASISTIO));
            data.put("noAsistio", citaRepository.countByEstado(EstadoCita.NO_ASISTIO));

            return new ApiResponse<>(true, "Estadísticas por estado", data);
        }
    }