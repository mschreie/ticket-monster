package org.jboss.jdf.example.ticketmonster.rest;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Singleton;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.jboss.jdf.example.ticketmonster.model.PriceCategory;
import org.jboss.jdf.example.ticketmonster.model.Show;

/**
 * @author Marius Bogoevici
 */
@Path("/shows")
@ApplicationScoped
@Singleton
public class ShowService extends BaseEntityService<Show> {

    public ShowService() {
        super(Show.class);
    }

    @Override
    protected Predicate[] extractPredicates(MultivaluedMap<String,
            String> queryParameters,
            CriteriaBuilder criteriaBuilder,
            Root<Show> root) {

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (queryParameters.containsKey("venue")) {
            String venue = queryParameters.getFirst("venue");
            predicates.add(criteriaBuilder.equal(root.get("venue").get("id"), venue));
        }

        if (queryParameters.containsKey("event")) {
            String event = queryParameters.getFirst("event");
            predicates.add(criteriaBuilder.equal(root.get("event").get("id"), event));
        }


        return predicates.toArray(new Predicate[]{});
    }
    
    @GET
    @Path("/{showId:[0-9][0-9]*}/pricing")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<Long,List<PriceCategory>> getPricing(@PathParam("showId") Long showId) {
        Query query = getEntityManager().createQuery("select pc from PriceCategory pc where pc.show.id = :showId order by pc.section.id");
        query.setParameter("showId", showId);
        List<PriceCategory> priceCategories = query.getResultList();
        
        Map<Long, List<PriceCategory>> priceCategoryMap = new LinkedHashMap<Long, List<PriceCategory>> ();
        for (PriceCategory priceCategory : priceCategories) {
           if (!priceCategoryMap.containsKey(priceCategory.getSection().getId())) {
               priceCategoryMap.put(priceCategory.getSection().getId(), new ArrayList<PriceCategory>());
           }
           priceCategoryMap.get(priceCategory.getSection().getId()).add(priceCategory);
        }
        return priceCategoryMap;
    }
}