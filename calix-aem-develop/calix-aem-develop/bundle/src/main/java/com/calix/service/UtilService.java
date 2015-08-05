package com.calix.service;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;


@Component
@Service(UtilService.class)
public class UtilService {
    private static final Logger logger = LoggerFactory.getLogger(UtilService.class);

    public void createNode(Node node, Resource childRsrc) {
        try {
            if (node != null && !node.hasNode(childRsrc.getName())) {
                logger.info("######### CREATING NODE: "+childRsrc.getPath());
                Node ret = node.addNode(childRsrc.getName(), "nt:unstructured");
                if(childRsrc.getResourceType()!=null) {
                    ret.setProperty("sling:resourceType",childRsrc.getResourceType());
                }
                ret.save();
            }
        } catch (Exception e) {
            logger.error("Error mesaage: ", e);
        }
    }

    public void createNode(Node node, String child) {
        try {
            if (node != null && !node.hasNode(child)) {
                Node ret = node.addNode(child, "nt:unstructured");
                ret.save();
            }
        } catch (Exception e) {
            logger.error("Error mesaage: ", e);
        }
    }
}
