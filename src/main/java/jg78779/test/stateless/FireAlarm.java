package jg78779.test.stateless;

import jg78779.test.template.Fire;
import jg78779.test.template.Room;
import jg78779.test.template.Sprinkler;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.FactHandle;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Junior on 4/27/14.
 */
public class FireAlarm {

    public static void main(String[] args) {
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(ResourceFactory.newClassPathResource("jg78779/fireAlarm.drl", FireAlarm.class),
                ResourceType.DRL);
        if (kbuilder.hasErrors()) {
            System.err.println(kbuilder.getErrors().toString());
        }
        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
        StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
        String[] names = new String[]{"kitchen", "bedroom", "office", "livingroom"};
        Map<String, Room> name2room = new HashMap<String, Room>();
        for (String name : names) {
            Room room = new Room(name);
            name2room.put(name, room);
            ksession.insert(room);
            Sprinkler sprinkler = new Sprinkler(room);
            ksession.insert(sprinkler);
        }
        ksession.fireAllRules();

        Fire kitchenFire = new Fire(name2room.get("kitchen"));
        Fire officeFire = new Fire(name2room.get("office"));
        FactHandle kitchenFireHandle = ksession.insert(kitchenFire);
        FactHandle officeFireHandle = ksession.insert(officeFire);
        ksession.fireAllRules();

        ksession.retract(kitchenFireHandle);
        ksession.retract(officeFireHandle);
        ksession.fireAllRules();
    }
}
