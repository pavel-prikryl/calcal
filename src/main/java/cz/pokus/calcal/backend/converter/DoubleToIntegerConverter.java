package cz.pokus.calcal.backend.converter;

import com.vaadin.data.Converter;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;

public class DoubleToIntegerConverter implements Converter<Double, Integer> {
    private static final long serialVersionUID = 7287998396542009564L;

    @Override
    public Result<Integer> convertToModel(Double value, ValueContext context) {

        return value != null ? Result.ok(value.intValue()) : null;
    }

    @Override
    public Double convertToPresentation(Integer value, ValueContext context) {
        return value != null ? new Double(value) : null;
    }

}
