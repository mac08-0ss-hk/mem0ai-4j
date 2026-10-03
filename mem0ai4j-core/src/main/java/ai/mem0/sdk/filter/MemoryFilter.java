package ai.mem0.sdk.filter;

import tools.jackson.databind.annotation.JsonSerialize;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@JsonSerialize(using = MemoryFilterSerializer.class)
public sealed interface MemoryFilter permits MemoryFilter.Field,
        MemoryFilter.Comparison, MemoryFilter.Logical, MemoryFilter.Raw {

    static MemoryFilter eq(String field, Object value){
        return new Field(field, value);
    }

    static MemoryFilter in(String field, List<?> values){
        return comparison(field, "in", values);
    }

    static MemoryFilter gte(String field, Object values){
        return comparison(field, "gte", values);
    }

    static MemoryFilter lte(String field, Object value){
        return comparison(field, "lte", value);
    }

    static MemoryFilter gt(String field, Object value){
        return comparison(field, "gt", value);
    }

    static MemoryFilter lt(String field, Object value){
        return comparison(field, "lt", value);
    }

    static MemoryFilter ne(String field, Object value){
        return comparison(field, "ne", value);
    }

    static MemoryFilter contains(String field, Object value){
        return comparison(field, "contains", value);
    }

    static MemoryFilter icontains(String field, Object value){
        return comparison(field, "icontains", value);
    }


    static MemoryFilter comparison(String field, String in, Object values) {
        return new Comparison(field, in, values);
    }

    static MemoryFilter and(MemoryFilter... filters) {
        requiredFilters(filters, "AND");
        return new Logical("AND", List.of(filters));
    }

    static MemoryFilter or(MemoryFilter... filters) {
        requiredFilters(filters, "OR");
        return new Logical("OR", List.of(filters));
    }

    static MemoryFilter not(MemoryFilter filter) {
        Objects.requireNonNull(filter);
        return new Logical("NOT", List.of(filter));
    }

    static MemoryFilter raw(Map<String, Object> values) {
        return new Raw(values);
    }


    record Field(String field, Object value) implements MemoryFilter {
        public Field {
            Objects.requireNonNull(field, "field");
        }
    }

    record Comparison(String field, String operator, Object value) implements MemoryFilter {
        public Comparison {
            Objects.requireNonNull(field, "field");
            Objects.requireNonNull(operator, "operator");
        }
    }

    record Logical(String operator, List<MemoryFilter> filters) implements MemoryFilter {
        public Logical {
            Objects.requireNonNull(operator, "operator");
            filters = List.copyOf(filters);
        }
    }

    record Raw(Map<String, Object> values) implements MemoryFilter {
        public Raw {
            values = Map.copyOf(values);
        }
    }
    private static void requiredFilters(MemoryFilter[] filters, String operator) {
        Objects.requireNonNull(filters, operator.toLowerCase() + "Filters");
        if (filters.length == 0) {
            throw new IllegalArgumentException(operator + " requires at least one filter");
        }
    }

}
