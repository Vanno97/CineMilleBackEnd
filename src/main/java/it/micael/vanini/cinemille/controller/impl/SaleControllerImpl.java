package it.micael.vanini.cinemille.controller.impl;

import it.micael.vanini.cinemille.controller.SaleController;
import it.micael.vanini.cinemille.model.Sala;
import it.micael.vanini.cinemille.service.SaleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/sale")
@CrossOrigin("http://localhost:4200")
public class SaleControllerImpl implements SaleController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SaleControllerImpl.class);

    private final SaleService saleService;

    public SaleControllerImpl(SaleService saleService) {
        this.saleService = saleService;
    }
    @Override
    public List<Sala> getAll() {
        LOGGER.info("REST API: Get all sale");
        return this.saleService.getAll();
    }
}
